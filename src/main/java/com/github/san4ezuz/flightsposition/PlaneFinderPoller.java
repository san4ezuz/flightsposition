package com.github.san4ezuz.flightsposition;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class PlaneFinderPoller {
    private WebClient client = WebClient.create("http://localhost:7634/flights");

    @NonNull
    private final FlightRepository repository;

    @Scheduled(fixedRate = 1000)
    private void pollPlanes() {
        connectionFactory.getConnection().serverCommands().flushDb();

        client.get()
                .retrieve()
                .bodyToFlux(Flight.class)
                .toStream()
                .forEach(repository::save);

        repository.findAll().forEach(System.out::println);
    }
}
