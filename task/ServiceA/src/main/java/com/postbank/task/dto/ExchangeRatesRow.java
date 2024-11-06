package com.postbank.task.dto;

import com.postbank.task.service.impl.LocalDateAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class ExchangeRatesRow {
    @XmlElement(name = "GOLD")
    private Integer gold;
    @XmlElement(name = "NAME_")
    private String name;
    @XmlElement(name = "CODE")
    private String code;
    @XmlElement(name = "RATIO")
    private Integer ratio;
    @XmlElement(name = "REVERSERATE")
    private BigDecimal reverseRate;
    @XmlElement(name = "RATE")
    private BigDecimal rate;
    @XmlElement(name = "CURR_DATE")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate currDate;
    @XmlElement(name = "F_STAR")
    private Integer fStar;

}

