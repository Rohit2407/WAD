<script setup>
import { ref } from 'vue'

import MovieList from './components/MovieList.vue'
import MovieForm from './components/MovieForm.vue'
import BookingForm from './components/BookingForm.vue'
import BookingList from './components/BookingList.vue'
import Report from './components/Report.vue'

const refresh = ref(0)
const selectedMovie = ref(null)

function refreshData() {
  refresh.value++
}

function editMovie(movie) {
  selectedMovie.value = movie
}

function finishMovie() {
  selectedMovie.value = null
  refreshData()
}
</script>

<template>
  <main class="container">
    <h1>Movie Ticket Booking System</h1>

    <section class="panel">
      <MovieForm
        :selectedMovie="selectedMovie"
        @saved="finishMovie"
        @cancel="selectedMovie = null"
      />
    </section>

    <section class="panel">
      <MovieList
        :refresh="refresh"
        @edit="editMovie"
        @changed="refreshData"
      />
    </section>

    <section class="panel">
      <BookingForm :refresh="refresh" @booked="refreshData" />
    </section>

    <section class="panel">
      <BookingList :refresh="refresh" @changed="refreshData" />
    </section>

    <section class="panel">
      <Report :refresh="refresh" />
    </section>
  </main>
</template>
