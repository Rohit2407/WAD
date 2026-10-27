package com.example.movie.repository;

import com.example.movie.model.Booking;
import org.bson.Document;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {

    // 4. Customer-wise report
    @Aggregation(pipeline = {
        "{ '$lookup': { 'from': 'movies', 'localField': 'movieId', 'foreignField': '_id', 'as': 'movie' } }",
        "{ '$unwind': '$movie' }",
        "{ '$group': { '_id': '$customerName', 'totalBookings': { '$sum': 1 }, 'totalSeats': { '$sum': '$numberOfSeats' }, 'movies': { '$addToSet': '$movie.title' } } }",
        "{ '$project': { '_id': 0, 'customerName': '$_id', 'totalBookings': 1, 'totalSeats': 1, 'movies': 1 } }",
        "{ '$sort': { 'totalSeats': -1 } }"
    })
    List<Document> customerReport();

    // 5. Detailed booking report
    @Aggregation(pipeline = {
        "{ '$lookup': { 'from': 'movies', 'localField': 'movieId', 'foreignField': '_id', 'as': 'movie' } }",
        "{ '$unwind': '$movie' }",
        "{ '$project': { '_id': 0, 'customerName': 1, 'movieTitle': '$movie.title', 'numberOfSeats': 1, 'availableSeats': '$movie.availableSeats' } }"
    })
    List<Document> bookingDetailsReport();
}
