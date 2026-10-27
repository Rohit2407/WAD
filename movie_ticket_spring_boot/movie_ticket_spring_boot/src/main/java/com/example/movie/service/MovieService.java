package com.example.movie.service;

import com.example.movie.model.Movie;
import com.example.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Movie addMovie(Movie movie) {
        movie.setId(null);
        movie.setAvailableSeats(movie.getTotalSeats());
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(String id) {
        return movieRepository.findById(id).get();
    }

    public Movie updateMovie(String id, Movie input) {
        Movie movie = movieRepository.findById(id).get();

        int bookedSeats = movie.getTotalSeats() - movie.getAvailableSeats();

        movie.setTitle(input.getTitle());
        movie.setGenre(input.getGenre());
        movie.setTotalSeats(input.getTotalSeats());
        movie.setAvailableSeats(input.getTotalSeats() - bookedSeats);

        return movieRepository.save(movie);
    }

    public void deleteMovie(String id) {
        movieRepository.deleteById(id);
    }
}
