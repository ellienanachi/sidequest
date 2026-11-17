import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    // While developing, React runs on http://localhost:5173 and Spring Boot on 8080.
    // Any request to /api/... is passed on to Spring Boot, so in React we can just
    // write fetch('/api/quests') without the full address and without CORS setup.
    proxy: {
      '/api': 'http://localhost:8080',
    },
  },
})
