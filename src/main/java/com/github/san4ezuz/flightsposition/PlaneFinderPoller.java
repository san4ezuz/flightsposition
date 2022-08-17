package com.github.san4ezuz.flightsposition;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@EnableScheduling
@Component
public class PlaneFinderPoller {
    private WebClient client = WebClient.create("http://localhost:7634/flights");
    private final RedisConnectionFactory connectionFactory;
    private final RedisOperations<String, Flight> redisOperations;

    PlaneFinderPoller(RedisConnectionFactory connectionFactory, RedisOperations<String, Flight> redisOperations) {
        this.connectionFactory = connectionFactory;
        this.redisOperations = redisOperations;
    }

    @Scheduled(fixedRate = 1000)
    private void pollPlanes() {
        connectionFactory.getConnection().serverCommands().flushDb();

        client.get()
                .retrieve()
                .bodyToFlux(Flight.class)
                .toStream()
                .forEach(fl -> redisOperations.opsForValue().set(fl.getIcao24(), fl));

        redisOperations.opsForValue()
                .getOperations()
                .keys("*")
                .forEach(fl -> System.out.println(redisOperations.opsForValue().get(fl)));
    }
}
