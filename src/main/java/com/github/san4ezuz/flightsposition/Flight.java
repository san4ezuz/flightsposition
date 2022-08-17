package com.github.san4ezuz.flightsposition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Flight {
    @Id
    private String icao24;
    private String estDepartureAirport;
    private String estArrivalAirport;
    private String callsign;
    @JsonProperty("lastSeen")
    private Instant lastSeenTime;
    @JsonProperty("firstSeen")
    private Instant firstSeenTime;
}
