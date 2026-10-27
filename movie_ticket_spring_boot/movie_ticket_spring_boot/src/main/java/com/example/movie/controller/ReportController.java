package com.example.movie.controller;

import com.example.movie.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/movies")
    public List<Document> movieReport() {
        return reportService.movieReport();
    }

    @GetMapping("/top-movies")
    public List<Document> topMovies() {
        return reportService.topMovies();
    }

    @GetMapping("/genres")
    public List<Document> genreReport() {
        return reportService.genreReport();
    }

    @GetMapping("/customers")
    public List<Document> customerReport() {
        return reportService.customerReport();
    }

    @GetMapping("/booking-details")
    public List<Document> bookingDetails() {
        return reportService.bookingDetails();
    }
}
