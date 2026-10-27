<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  refresh: { type: Number, required: true }
})

const emit = defineEmits(['edit', 'changed'])
const movies = ref([])
const error = ref('')

async function getMovies() {
  try {
    const response = await fetch('http://localhost:8080/api/movies')
    if (!response.ok) throw new Error('Request failed')
    movies.value = await response.json()
    error.value = ''
  } catch {
    error.value = 'Could not load movies. Check Spring Boot and CORS settings.'
  }
}

async function deleteMovie(id) {
  if (!confirm('Delete this movie?')) return

  try {
    const response = await fetch(
      'http://localhost:8080/api/movies/' + encodeURIComponent(id),
      { method: 'DELETE' }
    )

    if (!response.ok) {
      alert('Failed to delete movie')
      return
    }

    alert('Movie deleted')
    emit('changed')
    await getMovies()
  } catch {
    alert('Could not connect to Spring Boot')
  }
}

watch(() => props.refresh, getMovies, { immediate: true })
</script>

<template>
  <h2>Movies</h2>
  <p v-if="error" class="error">{{ error }}</p>

  <div class="table-wrap">
    <table>
      <thead>
        <tr>
          <th>Name</th>
          <th>Ticket Price</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="movie in movies" :key="movie.id">
          <td>{{ movie.name }}</td>
          <td>₹{{ movie.ticketprice }}</td>
          <td class="actions">
            <button @click="emit('edit', movie)">Edit</button>
            <button class="danger" @click="deleteMovie(movie.id)">Delete</button>
          </td>
        </tr>
        <tr v-if="movies.length === 0 && !error">
          <td colspan="3">No movies found.</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
