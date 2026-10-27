package com.example.movie.service;

import com.example.movie.model.Movie;
import com.example.movie.model.Booking;
import com.example.movie.repository.MovieRepository;
import com.example.movie.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final MovieRepository movieRepository;

    public Booking addBooking(Booking booking) {
        Movie movie = movieRepository.findById(booking.getMovieId()).get();

        if (booking.getNumberOfSeats() <= 0 ||
                movie.getAvailableSeats() < booking.getNumberOfSeats()) {
            throw new RuntimeException("Not enough seats");
        }

        movie.setAvailableSeats(
                movie.getAvailableSeats() - booking.getNumberOfSeats()
        );

        movieRepository.save(movie);
        booking.setId(null);
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(String id) {
        return bookingRepository.findById(id).get();
    }

    public Booking updateBooking(String id, Booking input) {
        Booking booking = bookingRepository.findById(id).get();

        if (input.getMovieId() != null &&
                !input.getMovieId().equals(booking.getMovieId())) {
            throw new RuntimeException(
                    "To change movies, delete this booking and create a new one"
            );
        }

        Movie movie = movieRepository.findById(booking.getMovieId()).get();
        int difference = input.getNumberOfSeats() - booking.getNumberOfSeats();

        if (input.getNumberOfSeats() <= 0) {
            throw new RuntimeException("Invalid number of seats");
        }

        if (difference > movie.getAvailableSeats()) {
            throw new RuntimeException("Not enough seats");
        }

        movie.setAvailableSeats(movie.getAvailableSeats() - difference);
        movieRepository.save(movie);

        booking.setCustomerName(input.getCustomerName());
        booking.setNumberOfSeats(input.getNumberOfSeats());

        return bookingRepository.save(booking);
    }

    public void deleteBooking(String id) {
        Booking booking = bookingRepository.findById(id).get();
        Movie movie = movieRepository.findById(booking.getMovieId()).get();

        movie.setAvailableSeats(
                movie.getAvailableSeats() + booking.getNumberOfSeats()
        );

        movieRepository.save(movie);
        bookingRepository.deleteById(id);
    }
}
