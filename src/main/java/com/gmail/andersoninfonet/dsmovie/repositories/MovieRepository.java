package com.gmail.andersoninfonet.dsmovie.repositories;

import com.gmail.andersoninfonet.dsmovie.entities.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    
}
