package com.postbank.task.controller;

import com.postbank.task.dto.ExchangeRates;
import com.postbank.task.service.CurrencyCourseService;
import com.postbank.task.ws.WebSocketSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@RestController
@RequestMapping("/api/download-currencies")
@RequiredArgsConstructor
public class CurrencyCourseController {

    private final WebSocketSender webSocketSender;

    private final CurrencyCourseService service;

    @GetMapping
    public ResponseEntity getCurrencyCourses() throws JAXBException, IOException {
        ExchangeRates exchangeRates = service.getCurrencyCourses();
        if(exchangeRates!=null) {
            try {
                webSocketSender.sendExchangeRates(exchangeRates);
                return ResponseEntity.ok("Exchange rates sent!");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error sending exchange rates " + e.getMessage());
            }
        }else {
            return ResponseEntity.ok("No new data!");
        }

    }


}
