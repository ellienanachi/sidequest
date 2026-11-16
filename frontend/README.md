# SideQuest/s frontend (React + Vite)

The React app for SideQuest/s. It gets all its data from the Spring Boot backend's `/api` endpoints.

## Run it

You need two terminals: one for the backend and one for the frontend.

```bash
# Terminal 1, project root: the backend on http://localhost:8080
.\mvnw.cmd spring-boot:run

# Terminal 2, this folder: the frontend on http://localhost:5173
cd frontend
npm install   # only the first time, or after package.json changes
npm run dev
```

Open <http://localhost:5173>.

## How React reaches the backend

`vite.config.js` has a proxy, so a request to `/api/...` from React is passed on to `http://localhost:8080/api/...`. That's why the code can call `fetch('/api/health')` without the full address.

## Files

| File | What it does |
|---|---|
| `index.html` | The single HTML page. React draws everything inside `<div id="root">`. |
| `src/main.jsx` | Starts React and renders `<App />` into `#root`. |
| `src/App.jsx` | The app itself. |
| `src/index.css` | Colors, fonts and styles from the Figma prototype. |
