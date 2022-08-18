package com.github.san4ezuz.flightsposition;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@AllArgsConstructor
@Configuration
public class FlightRetriever {
    private final FlightRepository flightRepository;
    private final WebSocketHandler webSocketHandler;

    @Bean
    Consumer<List<Flight>> retrieveFlight() {

        return flList -> {
            flightRepository.deleteAll();
            flightRepository.saveAll(flList);
            sendPositions();
        };
    }

    private void sendPositions() {
        if (flightRepository.count() > 0) {
            for (WebSocketSession sessionInList : webSocketHandler.getSessionList()) {
                try {
                    sessionInList.sendMessage(new TextMessage(flightRepository.findAll().toString()));
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
    }
}
