package com.gmail.andersoninfonet.dsmovie.repositories;

import java.util.Optional;

import com.gmail.andersoninfonet.dsmovie.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);

}
