// Hand-drawn style art for quest cards, like the doodles in Figma.
// variant 0 = star, variant 1 = smiley.
// In JSX, SVG attributes are camelCase: stroke-width becomes strokeWidth.
function Doodle({ variant }) {
  if (variant === 1) {
    return (
      <svg viewBox="0 0 100 100" fill="none" stroke="currentColor" strokeWidth="6" strokeLinecap="round" aria-hidden="true">
        <circle cx="50" cy="50" r="40" />
        <circle cx="38" cy="40" r="3" fill="currentColor" />
        <circle cx="62" cy="40" r="3" fill="currentColor" />
        <path d="M32 58q18 18 36 0" />
      </svg>
    )
  }

  return (
    <svg viewBox="0 0 100 100" fill="none" stroke="currentColor" strokeWidth="6" strokeLinejoin="round" aria-hidden="true">
      <path d="M30 12 40 40 70 30 52 52 74 84 44 66 24 88 30 58 8 46 36 38z" />
    </svg>
  )
}

export default Doodle
