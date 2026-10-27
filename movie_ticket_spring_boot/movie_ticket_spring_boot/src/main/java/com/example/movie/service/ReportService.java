package com.example.movie.service;

import com.example.movie.repository.MovieRepository;
import com.example.movie.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final MovieRepository movieRepository;
    private final BookingRepository bookingRepository;

    public List<Document> movieReport() {
        return movieRepository.movieBookingReport();
    }

    public List<Document> topMovies() {
        return movieRepository.topBookedMovies();
    }

    public List<Document> genreReport() {
        return movieRepository.genreReport();
    }

    public List<Document> customerReport() {
        return bookingRepository.customerReport();
    }

    public List<Document> bookingDetails() {
        return bookingRepository.bookingDetailsReport();
    }
}
