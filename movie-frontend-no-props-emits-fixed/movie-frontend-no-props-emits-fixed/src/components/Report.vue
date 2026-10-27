<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

const report = ref([])
const error = ref('')

async function getReport() {
  try {
    const response = await fetch('http://localhost:8080/api/report')
    if (!response.ok) throw new Error('Request failed')
    report.value = await response.json()
    error.value = ''
  } catch {
    error.value = 'Could not load report. Check Spring Boot and CORS settings.'
  }
}

onMounted(() => window.addEventListener('data-changed', getReport))
onUnmounted(() => window.removeEventListener('data-changed', getReport))
getReport()
</script>

<template>
  <h2>Booking Revenue Report</h2>
  <p v-if="error" class="error">{{ error }}</p>
  <div class="table-wrap">
    <table>
      <thead><tr><th>Customer</th><th>Movie</th><th>Ticket Price</th><th>Tickets</th><th>Revenue</th></tr></thead>
      <tbody>
        <tr v-for="(row, index) in report" :key="index">
          <td>{{ row.customername }}</td>
          <td>{{ row.moviename }}</td>
          <td>₹{{ row.ticketprice }}</td>
          <td>{{ row.tickets }}</td>
          <td>₹{{ row.revenue }}</td>
        </tr>
        <tr v-if="report.length === 0 && !error"><td colspan="5">No report data found.</td></tr>
      </tbody>
    </table>
  </div>
</template>
