package com.github.san4ezuz.flightsposition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
public class FlightsPositionApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightsPositionApplication.class, args);
    }

    @Bean
    public RedisOperations<String, Flight> redisOperations(RedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Flight> serializer = new Jackson2JsonRedisSerializer<>(Flight.class);
        RedisTemplate<String, Flight> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setDefaultSerializer(serializer);
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }
}
