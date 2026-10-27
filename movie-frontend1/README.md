# Movie Ticket Booking Vue Frontend

Vue 3 + Vite frontend for the Spring Boot Movie Booking API.

## Requirements

- Node.js and npm
- Spring Boot backend running at `http://localhost:8080`
- MongoDB running and configured in Spring Boot

## Run

```bash
npm install
npm run dev
```

Open the URL printed by Vite, usually `http://localhost:5173`.

## Backend requirements

The backend should expose:

- `GET /api/movies`
- `POST /api/movies`
- `GET /api/movies/id/{id}`
- `GET /api/movies/name/{name}`
- `PUT /api/movies/{id}`
- `DELETE /api/movies/{id}`
- `POST /api/bookings`
- `GET /api/bookings`
- `DELETE /api/bookings/{id}`
- `GET /api/report`

Allow CORS from `http://localhost:5173` on the Spring Boot controller, for example:

```java
@CrossOrigin(origins = "http://localhost:5173")
```

## Important field names

The frontend follows the model fields from the example backend:

- Movie: `id`, `name`, `ticketprice`
- Booking: `id`, `movieid`, `customername`, `tickets`
- Report: `customername`, `movieid`, `moviename`, `ticketprice`, `tickets`, `revenue`

The combined report is calculated by the Spring Boot service.
