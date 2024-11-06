package com.postbank.task.service;

import com.postbank.task.dto.ExchangeRates;

import java.io.IOException;

public interface CurrencyCourseService {

    void saveCurrency(ExchangeRates exchangeRates) throws IOException;

}
