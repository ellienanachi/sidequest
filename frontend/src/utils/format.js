// Small helpers that turn raw values from the API into text for the screen.
// "export" lets other files import them: import { peso } from '../utils/format.js'

// 150 -> "₱150"
export function peso(value) {
  return '₱' + Number(value).toLocaleString('en-PH', { maximumFractionDigits: 2 })
}

// "MEDIUM" -> "Medium"
export function titleCase(word) {
  return word ? word.charAt(0) + word.slice(1).toLowerCase() : ''
}

// "Mary Mendoza" -> "Mary M." like the Figma cards
export function shortName(name) {
  const parts = (name || '').split(' ').filter(Boolean)
  return parts.length > 1 ? parts[0] + ' ' + parts[parts.length - 1][0] + '.' : name
}
