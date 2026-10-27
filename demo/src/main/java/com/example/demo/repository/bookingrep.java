package com.example.demo.repository;

import com.example.demo.model.booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface bookingrep extends MongoRepository<booking, String> {

    List<booking> findByMovieid(String movieid);

    List<booking> findByCustomername(String customername);
}