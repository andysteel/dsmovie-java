package com.gmail.andersoninfonet.dsmovie.services;

import com.gmail.andersoninfonet.dsmovie.dto.MovieDTO;
import com.gmail.andersoninfonet.dsmovie.entities.Movie;
import com.gmail.andersoninfonet.dsmovie.repositories.MovieRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    
    private final MovieRepository movieRepository;

    public MovieService(final MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Page<MovieDTO> findAll(final Pageable pageable) {
        final Page<Movie> movies = this.movieRepository.findAll(pageable);
        return movies.map(MovieDTO::new);
    }

    public MovieDTO findById(final Long id) {
        final Movie movie = this.movieRepository.findById(id).orElseThrow(RuntimeException::new);
        return new MovieDTO(movie);
    }
}
