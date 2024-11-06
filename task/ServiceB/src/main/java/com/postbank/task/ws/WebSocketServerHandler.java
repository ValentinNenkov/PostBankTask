package com.postbank.task.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.postbank.task.dto.ExchangeRates;
import com.postbank.task.service.CurrencyCourseService;
import org.springframework.web.socket.CloseStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketServerHandler extends TextWebSocketHandler {

    private final CurrencyCourseService service;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public WebSocketServerHandler(CurrencyCourseService service) {
        this.service = service;
        objectMapper.registerModule(new JavaTimeModule());
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connection established with client: " + session.getId());
    }
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);

        ExchangeRates exchangeRates = objectMapper.readValue(payload, ExchangeRates.class);
        service.saveCurrency(exchangeRates);
        System.out.println("Parsed ExchangeRates object: " + exchangeRates);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Connection closed with client: " + session.getId());
    }
}
