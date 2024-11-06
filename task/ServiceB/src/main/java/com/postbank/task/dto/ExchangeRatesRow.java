package com.postbank.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRatesRow {
    private Integer gold;
    private String name;
    private String code;
    private Integer ratio;
    private BigDecimal reverseRate;
    private BigDecimal rate;
    private LocalDate currDate;
    private Integer fStar;

}

