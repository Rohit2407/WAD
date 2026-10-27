package com.example.demo.controller;

import com.example.demo.model.movie;
import com.example.demo.model.booking;
import com.example.demo.service.allserv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/api")
public class controller {

    @Autowired
    private allserv service;

    // Get all movies
    @GetMapping("/movies")
    public List<movie> getall() {
        return service.getall();
    }

    // Add movie
    @PostMapping("/movies")
    public movie addmovie(@RequestBody movie l1) {
        return service.addmovvie(l1);
    }

    // Find movie by ID
    @GetMapping("/movies/id/{id}")
    public Optional<movie> findmovie(@PathVariable String id) {
        return service.findmovie(id);
    }

    // Find movie by name
    @GetMapping("/movies/name/{name}")
    public Optional<movie> findmovie1(@PathVariable String name) {
        return service.findmovie1(name);
    }

    // Update movie
    @PutMapping("/movies/{id}")
    public movie updatemovie(
            @PathVariable String id,
            @RequestBody movie l1) {
        return service.updatemovie(id, l1);
    }

    // Delete movie
    @DeleteMapping("/movies/{id}")
    public String deletemovie(@PathVariable String id) {
        service.deletemovie(id);
        return "Movie deleted successfully";
    }

    // Add booking
    @PostMapping("/bookings")
    public booking addbooking(@RequestBody booking b1) {
        return service.addbooking(b1);
    }

    // Get all bookings
    @GetMapping("/bookings")
    public List<booking> getbookings() {
        return service.getbookings();
    }

    // Find booking by ID
    @GetMapping("/bookings/{id}")
    public Optional<booking> findbooking(@PathVariable String id) {
        return service.findbooking(id);
    }

    // Delete booking
    @DeleteMapping("/bookings/{id}")
    public String deletebooking(@PathVariable String id) {
        service.deletebooking(id);
        return "Booking deleted successfully";
    }

    // Combined report
    @GetMapping("/report")
    public List<Map<String, Object>> report() {
        return service.report();
    }
}