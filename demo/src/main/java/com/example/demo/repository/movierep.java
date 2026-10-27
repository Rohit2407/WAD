package com.example.demo.repository;

import com.example.demo.model.movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface movierep extends MongoRepository<movie,String> {
    Optional<movie> findByName(String name);
}
