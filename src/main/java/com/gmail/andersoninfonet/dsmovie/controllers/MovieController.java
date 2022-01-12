package com.gmail.andersoninfonet.dsmovie.controllers;

import com.gmail.andersoninfonet.dsmovie.dto.MovieDTO;
import com.gmail.andersoninfonet.dsmovie.services.MovieService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {
    
    private final MovieService movieService;

    public MovieController(final MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public Page<MovieDTO> findAll(final Pageable pageable) {
        return movieService.findAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public MovieDTO findById(@PathVariable final Long id) {
        return movieService.findById(id);
    }
}
