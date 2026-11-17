import js from '@eslint/js'
import globals from 'globals'
import reactHooks from 'eslint-plugin-react-hooks'
import reactRefresh from 'eslint-plugin-react-refresh'
import { defineConfig, globalIgnores } from 'eslint/config'

// ESLint checks our code for mistakes. Run it with: npm run lint
export default defineConfig([
  // Don't check the production build output.
  globalIgnores(['dist']),
  {
    files: ['**/*.{js,jsx}'],
    extends: [
      js.configs.recommended,               // general JavaScript mistakes (unused variables, typos in names...)
      reactHooks.configs.flat.recommended,  // the Rules of Hooks (useState/useEffect used correctly)
      reactRefresh.configs.vite,            // keeps Vite's hot reload working
    ],
    languageOptions: {
      globals: globals.browser, // fetch, document, window... exist in the browser
      parserOptions: {
        ecmaFeatures: { jsx: true },
      },
    },
    rules: {
      // Components used only in JSX (e.g. QuestCard) count as "used".
      'no-unused-vars': ['error', { varsIgnorePattern: '^[A-Z_]' }],
    },
  },
])
