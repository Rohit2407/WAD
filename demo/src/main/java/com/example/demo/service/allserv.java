package com.example.demo.service;

import com.example.demo.model.booking;
import com.example.demo.model.movie;
import com.example.demo.repository.bookingrep;
import com.example.demo.repository.movierep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class allserv {

    @Autowired
    private movierep rep1;

    @Autowired
    private bookingrep rep2;

    // Get all movies
    public List<movie> getall() {
        return rep1.findAll();
    }

    // Add movie
    public movie addmovvie(movie l1) {
        return rep1.save(l1);
    }

    // Find movie by ID
    public Optional<movie> findmovie(String id) {
        return rep1.findById(id);
    }

    // Find movie by name
    public Optional<movie> findmovie1(String name) {
        return rep1.findByName(name);
    }

    // Update movie
    public movie updatemovie(String id, movie l1) {
        l1.setId(id);
        return rep1.save(l1);
    }

    // Delete movie
    public void deletemovie(String id) {
        rep1.deleteById(id);
    }

    // Add booking
    public booking addbooking(booking b1) {
        return rep2.save(b1);
    }

    // Get all bookings
    public List<booking> getbookings() {
        return rep2.findAll();
    }

    // Find booking by ID
    public Optional<booking> findbooking(String id) {
        return rep2.findById(id);
    }

    // Delete booking
    public void deletebooking(String id) {
        rep2.deleteById(id);
    }

    // Combine movie and booking collections
    public List<Map<String, Object>> report() {

        List<booking> bookings = rep2.findAll();

        List<Map<String, Object>> rep = new ArrayList<>();

        for (booking Book : bookings) {

            Optional<movie> result =
                    rep1.findById(Book.getMovieid());

            if (result.isPresent()) {

                movie Movie = result.get();

                double revenue =
                        Movie.getTicketprice() * Book.getTickets();

                Map<String, Object> row = new HashMap<>();

                row.put("customername", Book.getCustomername());
                row.put("movieid", Book.getMovieid());
                row.put("moviename", Movie.getName());
                row.put("ticketprice", Movie.getTicketprice());
                row.put("tickets", Book.getTickets());
                row.put("revenue", revenue);

                rep.add(row);
            }
        }

        return rep;
    }
}