package com.github.san4ezuz.flightsposition;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FlightRepository extends ReactiveCrudRepository<Flight,String> {
}
