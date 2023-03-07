package com.gmail.andersoninfonet.dsmovie.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;

@SpringBootTest
@ActiveProfiles("test")
public class MovieServiceTest {
    
    @Autowired
    private MovieService movieService;

    @Test
    void deveLancarUmaExceptionCasoOLimiteSejaAntigido() {
        for (int i = 1; i <= 20; i++) {
            this.movieService.findById(1L);
        }
        Assertions.assertThatExceptionOfType(RequestNotPermitted.class)
            .isThrownBy(() -> this.movieService.findById(1L));
    }
}
