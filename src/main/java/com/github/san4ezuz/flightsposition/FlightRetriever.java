package com.github.san4ezuz.flightsposition;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Consumer;

@AllArgsConstructor
@Configuration
public class FlightRetriever {
    private final FlightRepository flightRepository;

    @Bean
    Consumer<List<Flight>> retrieveFlight() {

        return flList -> {
            flightRepository.deleteAll();
            flightRepository.saveAll(flList);
            flightRepository.findAll().forEach(System.out::println);
        };
    }
}
