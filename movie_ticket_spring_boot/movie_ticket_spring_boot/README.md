# Movie Ticket Booking — Spring Boot + MongoDB

## Dependencies
Use Spring Web, Spring Data MongoDB, and Lombok.

## Configuration
`src/main/resources/application.properties`:
`spring.data.mongodb.uri=mongodb://localhost:27017/movieDB`

## Endpoints

### Movies
- POST `/movies`
- GET `/movies`
- GET `/movies/{id}`
- PUT `/movies/{id}`
- DELETE `/movies/{id}`

### Bookings
- POST `/bookings`
- GET `/bookings`
- GET `/bookings/{id}`
- PUT `/bookings/{id}`
- DELETE `/bookings/{id}`

### Aggregation reports
- GET `/reports/movies` — booking count and seats booked per movie
- GET `/reports/top-movies` — top five movies by booked seats
- GET `/reports/genres` — totals grouped by genre
- GET `/reports/customers` — bookings and seats per customer
- GET `/reports/booking-details` — booking details joined with movie details

## Example requests

Create a movie (POST `/movies`):
```json
{
  "title": "Leo",
  "genre": "Action",
  "totalSeats": 100
}
```

Create a booking (POST `/bookings`), replacing the ID with the movie ID returned above:
```json
{
  "movieId": "PASTE_MOVIE_ID_HERE",
  "customerName": "Rohit",
  "numberOfSeats": 3
}
```

The booking service reduces the movie's available seats when a booking is created, adjusts the difference on update, and restores seats when a booking is deleted.

## Notes
- The code uses MongoDB collection names `movies` and `bookings`.
- `@MongoId(FieldType.STRING)` makes IDs stored as strings, matching `Booking.movieId` for `$lookup`.
- This is a simple learning implementation. The movie and booking writes are separate operations, not a multi-document transaction.
- `findById(id).get()` is kept simple; it throws if the ID does not exist.
