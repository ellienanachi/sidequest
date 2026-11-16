import { useEffect, useState } from 'react'

// Step 1: check that React can talk to Spring Boot.
// It calls GET /api/health once and shows what the server answers.
function App() {
  const [health, setHealth] = useState(null) // the JSON from the server, e.g. { status: "ok", app: "SideQuest/s" }
  const [error, setError] = useState(null)   // a message if the request fails

  // useEffect with [] runs once, right after the page first shows.
  useEffect(() => {
    fetch('/api/health')
      .then((response) => {
        if (!response.ok) throw new Error('HTTP ' + response.status)
        return response.json()
      })
      .then((data) => setHealth(data))
      .catch((err) => setError(err.message))
  }, [])

  return (
    <main className="page">
      <h1 className="brand">SIDEQUEST</h1>

      {/* Only one of these three shows, depending on the state above. */}
      {error && (
        <p className="status status-bad">
          Can't reach the backend ({error}). Is Spring Boot running on port 8080?
        </p>
      )}
      {!error && !health && <p className="status">Checking the backend...</p>}
      {health && (
        <p className="status status-ok">
          Backend says "{health.status}" from {health.app}
        </p>
      )}
    </main>
  )
}

export default App
