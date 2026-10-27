<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  selectedMovie: { type: Object, default: null }
})

const emit = defineEmits(['saved', 'cancel'])

const name = ref('')
const ticketprice = ref('')

watch(
  () => props.selectedMovie,
  (movie) => {
    name.value = movie ? movie.name : ''
    ticketprice.value = movie ? movie.ticketprice : ''
  },
  { immediate: true }
)

async function saveMovie() {
  if (!name.value.trim() || ticketprice.value === '' ||
      Number(ticketprice.value) <= 0) {
    alert('Enter a movie name and a valid ticket price')
    return
  }

  const movie = {
    name: name.value.trim(),
    ticketprice: Number(ticketprice.value)
  }

  let url = 'http://localhost:8080/api/movies'
  let method = 'POST'

  if (props.selectedMovie) {
    url += '/' + encodeURIComponent(props.selectedMovie.id)
    method = 'PUT'
  }

  try {
    const response = await fetch(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(movie)
    })

    if (!response.ok) {
      alert('Failed to save movie. Check the backend and API endpoint.')
      return
    }

    alert('Movie saved successfully')
    name.value = ''
    ticketprice.value = ''
    emit('saved')
  } catch (error) {
    alert('Could not connect to Spring Boot at localhost:8080')
  }
}
</script>

<template>
  <h2>{{ selectedMovie ? 'Update Movie' : 'Add Movie' }}</h2>

  <form @submit.prevent="saveMovie" class="form-row">
    <input v-model="name" placeholder="Movie name" required />

    <input
      v-model="ticketprice"
      type="number"
      min="0.01"
      step="0.01"
      placeholder="Ticket price"
      required
    />

    <button type="submit">
      {{ selectedMovie ? 'Update Movie' : 'Add Movie' }}
    </button>

    <button
      v-if="selectedMovie"
      type="button"
      class="secondary"
      @click="emit('cancel')"
    >
      Cancel
    </button>
  </form>
</template>
