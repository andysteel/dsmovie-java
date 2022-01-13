package com.gmail.andersoninfonet.dsmovie.controllers;

import com.gmail.andersoninfonet.dsmovie.dto.MovieDTO;
import com.gmail.andersoninfonet.dsmovie.dto.ScoreDTO;
import com.gmail.andersoninfonet.dsmovie.services.ScoreService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/scores")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PutMapping
    public MovieDTO saveScore(@RequestBody ScoreDTO scoreDTO) {
        return scoreService.saveScore(scoreDTO);
    }
}
