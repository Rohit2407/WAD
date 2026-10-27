package com.example.movie.repository;

import com.example.movie.model.Movie;
import org.bson.Document;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {

    // 1. Movie-wise booking report
    @Aggregation(pipeline = {
        "{ '$lookup': { 'from': 'bookings', 'localField': '_id', 'foreignField': 'movieId', 'as': 'bookings' } }",
        "{ '$project': { '_id': 0, 'title': 1, 'genre': 1, 'totalSeats': 1, 'availableSeats': 1, 'bookingCount': { '$size': '$bookings' }, 'seatsBooked': { '$sum': '$bookings.numberOfSeats' } } }"
    })
    List<Document> movieBookingReport();

    // 2. Top five movies by seats booked
    @Aggregation(pipeline = {
        "{ '$lookup': { 'from': 'bookings', 'localField': '_id', 'foreignField': 'movieId', 'as': 'bookings' } }",
        "{ '$project': { '_id': 0, 'title': 1, 'seatsBooked': { '$sum': '$bookings.numberOfSeats' } } }",
        "{ '$sort': { 'seatsBooked': -1 } }",
        "{ '$limit': 5 }"
    })
    List<Document> topBookedMovies();

    // 3. Genre-wise report
    @Aggregation(pipeline = {
        "{ '$lookup': { 'from': 'bookings', 'localField': '_id', 'foreignField': 'movieId', 'as': 'bookings' } }",
        "{ '$project': { 'genre': 1, 'totalSeats': 1, 'availableSeats': 1, 'seatsBooked': { '$sum': '$bookings.numberOfSeats' } } }",
        "{ '$group': { '_id': '$genre', 'totalMovies': { '$sum': 1 }, 'totalSeats': { '$sum': '$totalSeats' }, 'availableSeats': { '$sum': '$availableSeats' }, 'seatsBooked': { '$sum': '$seatsBooked' } } }",
        "{ '$project': { '_id': 0, 'genre': '$_id', 'totalMovies': 1, 'totalSeats': 1, 'availableSeats': 1, 'seatsBooked': 1 } }"
    })
    List<Document> genreReport();
}
