package com.github.san4ezuz.flightsposition;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class FlightController {

    @NonNull
    private final FlightRepository repository;

    @GetMapping("/aircraft")
    private String getCurrentFlights(Model model) {
        model.addAttribute("currentFlights", repository.findAll());
        return "positions";
    }
}
