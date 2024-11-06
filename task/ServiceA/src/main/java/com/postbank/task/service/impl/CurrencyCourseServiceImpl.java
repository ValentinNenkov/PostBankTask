package com.postbank.task.service.impl;

import com.postbank.task.dto.ExchangeRates;
import com.postbank.task.dto.ExchangeRatesRow;
import com.postbank.task.entity.CurrencyCourse;
import com.postbank.task.entity.CurrencyCourseRow;
import com.postbank.task.repository.CurrencyCourseRepository;
import com.postbank.task.service.CurrencyCourseService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyCourseServiceImpl implements CurrencyCourseService {

    private final CurrencyCourseRepository currencyCourseRepository;
    @Value("${bnb.currency.courses.url}")
    String bnbUrl;

    @Override
    @Transactional
    public ExchangeRates getCurrencyCourses() throws IOException, JAXBException {


        RestTemplate restTemplate = new RestTemplate();
        String xmlResponse = restTemplate.getForObject(bnbUrl, String.class);

        xmlResponse = xmlResponse.trim();
        xmlResponse = removeBOM(xmlResponse);

        if (!xmlResponse.startsWith("<?xml")) {
            System.out.println("The content is not valid XML.");
        }

        JAXBContext context = JAXBContext.newInstance(ExchangeRates.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xmlResponse);
        ExchangeRates exchangeRates = (ExchangeRates) unmarshaller.unmarshal(reader);

        CurrencyCourse currencyCourseFromBNB = fromExchangeRatesToCurrencyCourse(exchangeRates);

        CurrencyCourse currencyCourse = currencyCourseRepository.findByActive(Boolean.TRUE)
                .orElse(null);

        if (currencyCourse == null) {
            currencyCourseFromBNB.setActive(true);
            currencyCourseRepository.save(currencyCourseFromBNB);
            return exchangeRates;
        } else {
            if (!compareCurrencyCourses(currencyCourse, currencyCourseFromBNB)) {
                currencyCourse.setActive(false);
                currencyCourseFromBNB.setActive(true);
                currencyCourseRepository.save(currencyCourse);
                currencyCourseRepository.save(currencyCourseFromBNB);
                return exchangeRates;
            }
        }

        return null;
    }

    private boolean compareCurrencyCourses(CurrencyCourse currencyCourse, CurrencyCourse currencyCourseFromBNB) {
        List<CurrencyCourseRow> rows1 = currencyCourse.getRows();
        List<CurrencyCourseRow> rows2 = currencyCourseFromBNB.getRows();

        if (rows1.size() != rows2.size()) {
            return false;
        }

        for (int i = 0; i < rows1.size(); i++) {
            CurrencyCourseRow row1 = rows1.get(i);
            CurrencyCourseRow row2 = rows2.get(i);
            if(row2.getFStar()!=0){
                continue;
            }

            if (!row1.getCurrDate().equals(row2.getCurrDate()) ||
                    row1.getRate().compareTo(row2.getRate()) != 0) {
                return false;
            }
        }

        return true;
    }


    private CurrencyCourse fromExchangeRatesToCurrencyCourse(ExchangeRates exchangeRates){
        CurrencyCourse currencyCourse = new CurrencyCourse();
        List<CurrencyCourseRow> currencyCourseRows =
                exchangeRates.getExchangeRatesRows().stream()
                        .skip(1)
                        .map(this::fromExchangeRatesRowToCurrencyCourseRow)
                        .collect(Collectors.toList());
        currencyCourseRows.forEach(cr -> cr.setCurrencyCourse(currencyCourse));
        currencyCourse.setRows(currencyCourseRows);
        currencyCourse.setDate(LocalDate.now());
        return currencyCourse;
    }

    private CurrencyCourseRow fromExchangeRatesRowToCurrencyCourseRow(ExchangeRatesRow exchangeRatesRow) {
        return CurrencyCourseRow.builder()
                .gold(exchangeRatesRow.getGold())
                .name(exchangeRatesRow.getName())
                .code(exchangeRatesRow.getCode())
                .ratio(exchangeRatesRow.getRatio())
                .reverseRate(exchangeRatesRow.getReverseRate())
                .rate(exchangeRatesRow.getRate())
                .currDate(exchangeRatesRow.getCurrDate())
                .fStar(exchangeRatesRow.getFStar())
                .build();
    }

    private String removeBOM(String xmlResponse) throws IOException {
        BOMInputStream bomInputStream = new BOMInputStream(new ByteArrayInputStream(xmlResponse.getBytes(StandardCharsets.UTF_8)));
        return new String(bomInputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

}

