package com.gmail.andersoninfonet.dsmovie.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.andersoninfonet.dsmovie.dto.MovieDTO;
import com.gmail.andersoninfonet.dsmovie.dto.ScoreDTO;
import com.gmail.andersoninfonet.dsmovie.entities.Score;
import com.gmail.andersoninfonet.dsmovie.entities.User;
import com.gmail.andersoninfonet.dsmovie.repositories.MovieRepository;
import com.gmail.andersoninfonet.dsmovie.repositories.ScoreRepository;
import com.gmail.andersoninfonet.dsmovie.repositories.UserRepository;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@Service
public class ScoreService {
    
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final ScoreRepository scoreRepository;

    public ScoreService(MovieRepository movieRepository, UserRepository userRepository,
            ScoreRepository scoreRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.scoreRepository = scoreRepository;
    }

    @Transactional
    @RateLimiter(name = "movie-rate-limiter")
    public MovieDTO saveScore(ScoreDTO scoreDTO) {

        Runnable updateScoreForNewUser = () -> {
            var user = new User(scoreDTO.email());
            User userSaved = this.userRepository.saveAndFlush(user);

            updateScore(scoreDTO, userSaved);
        };

        this.userRepository.findByEmail(scoreDTO.email())
        .ifPresentOrElse( user -> updateScore(scoreDTO, user), updateScoreForNewUser);

        return new MovieDTO(this.movieRepository.getById(scoreDTO.movieId()));
    }

    @RateLimiter(name = "movie-rate-limiter")
    private void updateScore(ScoreDTO scoreDTO, User userSaved) {
        this.movieRepository.findById(scoreDTO.movieId())
        .ifPresent(movie -> {
            var score = new Score();
            score.setMovie(movie);
            score.setUser(userSaved);
            score.setValue(scoreDTO.score());

            score = this.scoreRepository.saveAndFlush(score);

            double total = movie.getScores()
            .stream()
            .reduce(0.0, (subtotal, m) -> subtotal + m.getValue(), Double::sum);

            double avg = total / movie.getScores().size();

            movie.setScore(avg);
            movie.setCount(movie.getScores().size());

            this.movieRepository.saveAndFlush(movie);
        });
    }
}
