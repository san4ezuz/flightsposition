package com.github.san4ezuz.flightsposition;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
@RequiredArgsConstructor
public class FlightController {
    private WebClient client = WebClient.create("http://localhost:7634/flights");

    @NonNull
    private final FlightRepository repository;

    @GetMapping("/aircraft")
    private String getCurrentFlights(Model model) {
        repository.deleteAll();

        client.get()
                .retrieve()
                .bodyToFlux(Flight.class)
                .toStream()
                .forEach(repository::save);

        model.addAttribute("currentFlights", repository.findAll());
        return "positions";
    }
}
