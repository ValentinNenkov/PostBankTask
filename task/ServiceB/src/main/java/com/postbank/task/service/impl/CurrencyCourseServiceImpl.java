package com.postbank.task.service.impl;

import com.postbank.task.dto.ExchangeRates;
import com.postbank.task.dto.ExchangeRatesRow;
import com.postbank.task.entity.CurrencyCourse;
import com.postbank.task.entity.CurrencyCourseRow;
import com.postbank.task.repository.CurrencyCourseRepository;
import com.postbank.task.service.CurrencyCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyCourseServiceImpl implements CurrencyCourseService {

    private final CurrencyCourseRepository currencyCourseRepository;

    @Override
    @Transactional
    public void saveCurrency(ExchangeRates exchangeRates) {

        CurrencyCourse currencyCourseFromBNB = fromExchangeRatesToCurrencyCourse(exchangeRates);

        currencyCourseFromBNB.setActive(true);
        currencyCourseRepository.save(currencyCourseFromBNB);

    }

    private CurrencyCourse fromExchangeRatesToCurrencyCourse(ExchangeRates exchangeRates) {
        CurrencyCourse currencyCourse = new CurrencyCourse();
        List<CurrencyCourseRow> currencyCourseRows = exchangeRates.getExchangeRatesRows().stream()
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


}

