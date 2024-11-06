package com.postbank.task.service;

import com.postbank.task.dto.ExchangeRates;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CurrencyCourseService {

    ExchangeRates getCurrencyCourses() throws IOException, JAXBException;

}
