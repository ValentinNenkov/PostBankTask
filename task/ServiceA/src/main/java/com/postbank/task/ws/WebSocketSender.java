package com.postbank.task.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.postbank.task.dto.ExchangeRates;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Service
public class WebSocketSender {

    private WebSocketSession session;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WebSocketSender() {

        objectMapper.registerModule(new JavaTimeModule());
    }

    @PostConstruct
    public void connect() throws Exception {
        WebSocketClient client = new StandardWebSocketClient();
        session = client.doHandshake(new MyWebSocketHandler(), "ws://localhost:8081/ws").get();
        if (session != null && session.isOpen()) {
            System.out.println("WebSocket connection established with Service B");
        } else {
            System.out.println("Failed to establish WebSocket connection with Service B");
        }
    }

    public void sendExchangeRates(ExchangeRates exchangeRates) throws Exception {
        String jsonMessage = objectMapper.writeValueAsString(exchangeRates);

        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(jsonMessage));
            System.out.println("Message sent to Service B: " + jsonMessage);
        } else {
            System.out.println("WebSocket session is closed");
        }
    }
}
