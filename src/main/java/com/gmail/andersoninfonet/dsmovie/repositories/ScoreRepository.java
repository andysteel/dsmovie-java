package com.gmail.andersoninfonet.dsmovie.repositories;

import com.gmail.andersoninfonet.dsmovie.entities.Score;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    
}
