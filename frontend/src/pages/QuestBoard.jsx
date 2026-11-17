import { useEffect, useState } from 'react'
import QuestCard from '../components/QuestCard.jsx'

// The Quest Board ("Near you"): every open quest from GET /api/quests.
// A page is just a component too. We keep pages in src/pages and the
// smaller pieces they use in src/components.
function QuestBoard() {
  const [quests, setQuests] = useState([])    // starts empty, filled by the fetch
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  // Same pattern as the health check: fetch once when the page first shows.
  useEffect(() => {
    fetch('/api/quests')
      .then((response) => {
        if (!response.ok) throw new Error('HTTP ' + response.status)
        return response.json()
      })
      .then((data) => setQuests(data))
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false)) // runs after success or failure
  }, [])

  return (
    // <> ... </> is a Fragment: it groups elements without adding an extra div.
    <>
      <header className="header">
        <h1 className="brand">SIDEQUEST</h1>
      </header>

      <main className="content">
        <h2 className="title-xl">NEAR YOU!!</h2>

        {loading && <p className="state">Loading quests...</p>}

        {error && (
          <p className="state">
            Couldn't load quests ({error}). Is Spring Boot running on port 8080?
          </p>
        )}

        {!loading && !error && quests.length === 0 && (
          <p className="state">No open quests right now. Check back later!</p>
        )}

        {quests.length > 0 && (
          <>
            <p className="count">{quests.length} open quests</p>
            <div className="grid">
              {/* One QuestCard per quest. "key" helps React tell the cards apart. */}
              {quests.map((quest) => (
                <QuestCard key={quest.id} quest={quest} />
              ))}
            </div>
          </>
        )}
      </main>
    </>
  )
}

export default QuestBoard
