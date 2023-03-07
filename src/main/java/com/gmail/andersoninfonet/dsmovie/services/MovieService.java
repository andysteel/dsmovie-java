package com.gmail.andersoninfonet.dsmovie.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmail.andersoninfonet.dsmovie.dto.MovieDTO;
import com.gmail.andersoninfonet.dsmovie.entities.Movie;
import com.gmail.andersoninfonet.dsmovie.repositories.MovieRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class MovieService {

    private final Logger logger = LoggerFactory.getLogger(MovieService.class);
    
    private final MovieRepository movieRepository;
    private final Map<Integer, Page<MovieDTO>> CACHE_PAGE = new HashMap<>();
    private final Map<Long, MovieDTO> CACHE_MOVIE = new HashMap<>();

    public MovieService(final MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @CircuitBreaker(name = "backend-movie", fallbackMethod = "carregarMoviesPageCache")
    public Page<MovieDTO> findAll(final Pageable pageable) {
        final Page<Movie> movies = this.movieRepository.findAll(pageable);

        var moviesPage = movies.map(MovieDTO::new);

        this.CACHE_PAGE.computeIfAbsent(pageable.getPageNumber(), page -> moviesPage); 
        return moviesPage;
    }

    @CircuitBreaker(name = "backend-movie", fallbackMethod = "carregarMovieCache")
    public MovieDTO findById(final Long id) {
        final Movie movie = this.movieRepository.findById(id).orElseThrow(RuntimeException::new);
        
        var movieDTO = new MovieDTO(movie);

        this.CACHE_MOVIE.computeIfAbsent(movieDTO.id(), page -> movieDTO); 
        return movieDTO;
    }

    private Page<MovieDTO> carregarMoviesPageCache(Pageable pageable) {
        logger.info("Consultando Page<MovieDTO> no cache");
        Page<MovieDTO> paginaVazia = new PageImpl<>(Collections.emptyList(), pageable, 0);
        return this.CACHE_PAGE.getOrDefault(pageable.getPageNumber(), paginaVazia);
    }

    private MovieDTO carregarMovieCache(Long id) {
        logger.info("Consultando MovieDTO no cache");
        return this.CACHE_MOVIE.getOrDefault(id, new MovieDTO(0L, "", 0.0, 0, ""));
    }
}
