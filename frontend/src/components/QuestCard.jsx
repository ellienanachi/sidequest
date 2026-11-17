import Doodle from './Doodle.jsx'
import { peso, shortName, titleCase } from '../utils/format.js'

// One quest on the board.
// It gets everything it shows through the "quest" prop, so the same component
// can draw any quest: <QuestCard quest={someQuest} />
function QuestCard({ quest }) {
  return (
    <article className="card">
      <div className="card-art">
        {/* Alternate the art: even ids get the star, odd ids the smiley. */}
        <Doodle variant={quest.id % 2} />
      </div>

      <div className="card-body">
        <h3 className="card-title">{quest.title}</h3>
        <p className="card-poster">{shortName(quest.posterName)}</p>
        <p className="card-desc">{quest.description}</p>

        <div className="card-meta">
          <span className="tag reward">{peso(quest.reward)}</span>
          <span className="tag">{titleCase(quest.size)}</span>
          <span className="tag">{quest.categoryName}</span>
          {/* Only show the location tag when the quest has one. */}
          {quest.location && <span className="tag">{quest.location}</span>}
        </div>

        {/* Will open the Quest Details page once that page is in React. */}
        <button className="btn-tan" type="button">See more</button>
      </div>
    </article>
  )
}

export default QuestCard
