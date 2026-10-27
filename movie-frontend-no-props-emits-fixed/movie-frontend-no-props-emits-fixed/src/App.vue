<script setup>
import MovieForm from './components/MovieForm.vue'
import MovieList from './components/MovieList.vue'
import BookingForm from './components/BookingForm.vue'
import BookingList from './components/BookingList.vue'
import Report from './components/Report.vue'
</script>

<template>
  <h1>Movie Ticket Booking</h1>

  <MovieForm />
  <MovieList />

  <BookingForm />
  <BookingList />

  <Report />
</template>