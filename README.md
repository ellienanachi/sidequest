# SideQuest/s

A guild quest board for CIT-U students. Verified Wildcats post what they need help with as **quests** (printing, tutoring, errands, borrowing equipment), and other students accept and complete them for a reward held in **escrow**. An **energy** system keeps the board fair, and ratings, reviews, guild ranks and admin moderation keep it trustworthy.

It is a web app that also works on phones (the layout switches to a bottom bar on small screens).

> **Status:** in progress. The database (all 15 tables from the ERD), the workbook rules, a simulated payment gateway, read-only endpoints and a React Quest Board are in place. Objective 5 has guild ranks, profiles and reviews. Posting and accepting quests, paying, login, reports and moderation are still TODO.
>
> **Source of truth:** the requirements workbook (`SML_Software_Requirements_Deliverables.xlsx`). The Figma file is the visual reference.

## Team and objectives

| Objective | Modules | Owner | Package |
|---|---|---|---|
| 1. Account | Account Management | Hynes Gavin Daugdaug | `account` |
| 2. Quests | Quest Management, Quest Reposting | Hynes Gavin Daugdaug | `quest` |
| 3. Discovery | Discovery & Browsing (Quest Board), Quest Alerts | Sean Riley Dela Cruz | `discovery` |
| 4. Transaction | Quest & Reward Transaction, Energy System and Subscription | Sean Riley Dela Cruz | `transaction` |
| 5. Community | Community Engagement, Guild Rank | Chad Ellie Sanchez | `community` |
| 5. Moderation | Admin & Moderation | Chad Ellie Sanchez | `admin` |
| Shared | Health check, errors, dev sample data | everyone | `common` |

## Tech stack

| Layer | Choice |
|---|---|
| Language | Java 25 (LTS) |
| Framework | Spring Boot 3.5.x (Web, Data JPA, Validation, DevTools) |
| Build | Maven (wrapper included: `mvnw` / `mvnw.cmd`) |
| Database | H2 in-memory (dev), PostgreSQL on Render (prod) |
| Payments | Simulated gateway now; PayMongo test mode later (GCash, Maya, QR Ph) |
| Frontend | React 19 + Vite (JavaScript) in `frontend/`. The old plain HTML pages in `src/main/resources/static` are being moved over to React. |
| Security | None yet (TODO: Spring Security + BCrypt) |

## How to run

Requirements: JDK 25 with `JAVA_HOME` pointing to it, and Node.js 20 or newer. Maven is not needed because the wrapper downloads it.

You need two terminals: one for the backend and one for the frontend.

```bash
# Terminal 1, project root: Spring Boot backend on http://localhost:8080
.\mvnw.cmd spring-boot:run        # Windows PowerShell (VS Code terminal)
./mvnw spring-boot:run            # Git Bash, macOS, Linux

# Terminal 2: React frontend on http://localhost:5173
cd frontend
npm install                       # only the first time
npm run dev
```

Open <http://localhost:5173> for the React app. While developing, Vite passes every `/api/...` request on to Spring Boot (see `frontend/vite.config.js`). The old HTML Quest Board is still at <http://localhost:8080> until it's moved to React.

The backend uses the `dev` profile: an H2 database that resets on every restart, filled with sample data.

Run the tests with `./mvnw test`.

### Seeing the ERD in MySQL Workbench (optional)

The `mysql` profile uses a local MySQL server instead of H2, so MySQL Workbench can draw the ERD from the real tables. You need MySQL Server 8 running on port 3306.

```powershell
$env:MYSQL_PASSWORD="your MySQL root password"   # never put it in a file
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=mysql"
```

Hibernate creates the `sidequest` schema, all 15 tables and their foreign keys, and the sample data is added. Set `MYSQL_DB` to use another schema name, and `MYSQL_USER` if you don't log in as `root`. Then in Workbench: **Database → Reverse Engineer**, connect, tick `sidequest`, and finish. The EER diagram shows every table with its relationship lines.

| URL | What it shows |
|---|---|
| `/` | Quest Board ("Near you"): search, location, size and category filters |
| `/quest.html?id=1` | Quest details: what is needed, reward, payout after commission |
| `GET /api/health` | `{"status":"ok","app":"SideQuest/s"}` |
| `GET /api/quests` | Open quests. Filters: `q`, `categoryId`, `size` (`SMALL`, `MEDIUM`, `LARGE`), `location` |
| `GET /api/quests/{id}` | One quest with poster, reward and payout |
| `GET /api/categories` | Quest categories |
| `GET /api/quest-sizes` | Reward range and energy cost per size |
| `GET /api/users` | Users with live energy (never the password hash) |
| `GET /api/acceptances` | Quest acceptances |
| `GET /api/profiles/{userId}` | Public profile: program, guild rank with progress to the next rank, average rating and reviews received (no email or student ID) |
| `POST /api/reviews` | Review the other side of a completed quest. Body: `{"acceptanceId": 3, "reviewerId": 1, "rating": 5, "comment": "..."}`. 4 or 5 stars gives the reviewed person +5 rank points. Errors: 400 (rule broken or invalid body), 404, 409 (already reviewed) |
| `/h2-console` | Database console: JDBC URL `jdbc:h2:mem:sidequest`, user `sa`, no password |

### Sample data (dev profile only)

`DevDataSeeder` adds 6 students and 1 admin, 6 categories, 6 open quests, 1 quest waiting for admin review, 1 quest in progress, and 3 completed quests with reviews (so profiles have reviews to show). Names and quests follow the Figma prototype. Each student has sample rank points so every rank shows up, from Bea (Recruit) to Jamie (Legend); Juan is a Squire, like the Figma profile. Every password hash is the placeholder `CHANGE_ME`; these are not real accounts.

## Workbook rules in the code

| Rule | Where |
|---|---|
| Sizes: Small ₱30–99 (1 energy), Medium ₱100–249 (2), Large ₱250–500 (3) | `quest/entity/QuestSize` |
| Energy: cap 10 (free) or 20 (Guild Pass), +1 every 30 minutes, accepting costs 1, completing gives 1 back | `transaction/service/EnergyRules` |
| 10% commission taken when the reward is released; Guild Pass ₱49/month | `transaction/service/RewardRules` |
| Payment provider interface (charge, refund) | `transaction/gateway/PaymentGateway` |
| Guild ranks: Recruit 0, Page 30, Squire 80, Knight 160, Champion 300, Legend 500 points (team decision, not in the workbook) | `community/entity/Rank` |
| Rank points: +10 to the adventurer per completed quest, +5 for receiving a 4 or 5 star review | `community/service/GuildRankRules` |

These rules have unit tests in `src/test`.

### Status values

Quests: `PENDING` (waiting for admin) → `OPEN` (on the board) → `IN_PROGRESS` → `COMPLETED`, or `CANCELLED`, `REJECTED`, `EXPIRED`.

Acceptances (the escrow steps): `ACCEPTED` → `FUNDED` → `DONE` → `COMPLETED`, or `DISPUTED`, `CANCELLED`, `REFUNDED`.

## Package layout

Base package `com.sidequest`. Each feature package has `entity/`, `repository/`, `service/`, `controller/` and `dto/`.

```
com.sidequest
├── account       users, profiles
├── quest         categories, quests, quest_photos
├── discovery     saved_quests, category_follows, notifications
├── transaction   quest_acceptances, payments, energy_transactions, gateway/
├── community     guild_ranks, messages, reviews
├── admin         reports
└── common        config (DevDataSeeder), web (health, errors)
```

Conventions:

- Controllers return DTOs (Java records), never entities. `passwordHash` is never exposed.
- Errors are JSON: `{"error": "..."}`. Throw `NotFoundException` (404), `BadRequestException` (400) or `ConflictException` (409) from `common/web`; `@Valid` failures also become 400.
- All relationships are `LAZY` and `spring.jpa.open-in-view=false`, so DTOs are built inside `@Transactional` service methods.
- Table and column names follow the ERD exactly (`users`, `poster_id`, `is_read`, ...).

## Deploying (later)

The workbook plans Render (web service + PostgreSQL). Set these environment variables on the server:

| Variable | Example |
|---|---|
| `SPRING_PROFILES_ACTIVE` | `prod` |
| `DB_URL` | `jdbc:postgresql://<host>:5432/sidequest` |
| `DB_USERNAME`, `DB_PASSWORD` | from the Render database page |

Never commit real passwords. `.env` files are git-ignored.

## Open questions

- How student IDs are verified (photo upload, admin check, ...).
- Messages in the ERD have no receiver or read flag, but Figma has an "unread conversations" popup.
- Energy and refund rules when a quest is cancelled, and the refund window.
- What happens if a quest giver never funds an accepted quest.

## TODO

- Move the Quest Board and Quest Details pages to React
- Spring Security: register with CIT-U email, log in, roles, admin-only endpoints
- Create/update endpoints with request DTOs and validation
- Accept → fund → done → confirm flow, Guild Pass, ads
- PayMongo test-mode gateway
- Objective 5: reports, admin moderation (approve or reject quests, suspend or ban users), messaging. Guild ranks, profiles and reviews are done.
