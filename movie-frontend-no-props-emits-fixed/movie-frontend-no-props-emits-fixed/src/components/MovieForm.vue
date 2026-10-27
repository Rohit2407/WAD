<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

const selectedMovie = ref(null)
const name = ref('')
const ticketprice = ref('')

function startEdit(event) {
  const movie = event.detail
  selectedMovie.value = movie
  name.value = movie.name
  ticketprice.value = movie.ticketprice
}

function cancelEdit() {
  selectedMovie.value = null
  name.value = ''
  ticketprice.value = ''
}

async function saveMovie() {
  if (!name.value.trim() || ticketprice.value === '' || Number(ticketprice.value) <= 0) {
    alert('Enter a movie name and a valid ticket price')
    return
  }

  const movie = {
    name: name.value.trim(),
    ticketprice: Number(ticketprice.value)
  }

  let url = 'http://localhost:8080/api/movies'
  let method = 'POST'

  if (selectedMovie.value) {
    url += '/' + encodeURIComponent(selectedMovie.value.id)
    method = 'PUT'
  }

  try {
    const response = await fetch(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(movie)
    })

    if (!response.ok) {
      alert('Failed to save movie')
      return
    }

    alert('Movie saved successfully')
    cancelEdit()
    window.dispatchEvent(new Event('data-changed'))
  } catch {
    alert('Could not connect to Spring Boot at localhost:8080')
  }
}

onMounted(() => {
  window.addEventListener('edit-movie', startEdit)
  window.addEventListener('cancel-movie-edit', cancelEdit)
})

onUnmounted(() => {
  window.removeEventListener('edit-movie', startEdit)
  window.removeEventListener('cancel-movie-edit', cancelEdit)
})
</script>

<template>
  <h2>{{ selectedMovie ? 'Update Movie' : 'Add Movie' }}</h2>
  <form class="form-row" @submit.prevent="saveMovie">
    <input v-model="name" placeholder="Movie name" required>
    <input v-model="ticketprice" type="number" min="0.01" step="0.01" placeholder="Ticket price" required>
    <button type="submit">{{ selectedMovie ? 'Update Movie' : 'Add Movie' }}</button>
    <button v-if="selectedMovie" type="button" class="secondary"
      @click="window.dispatchEvent(new Event('cancel-movie-edit'))">Cancel</button>
  </form>
</template>
