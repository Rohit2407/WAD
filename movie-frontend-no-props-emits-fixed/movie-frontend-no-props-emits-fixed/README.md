# Movie Ticket Booking Vue Frontend

This version does not use Vue component props or emits. Components communicate using browser custom events such as `edit-movie` and `data-changed`.

## Run
```bash
npm install
npm run dev
```

Open the Vite URL, normally http://localhost:5173.

Keep Spring Boot running at http://localhost:8080 and enable CORS for http://localhost:5173.

Required endpoints:
- GET/POST `/api/movies`
- PUT/DELETE `/api/movies/{id}`
- GET/POST `/api/bookings`
- DELETE `/api/bookings/{id}`
- GET `/api/report`
