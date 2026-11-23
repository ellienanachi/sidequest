# SideQuest/s — skeleton (practice branch)

> **This branch is for practice only. Never merge it into `main`.**
> The real app is on `main`.

This is SideQuest/s with every feature removed. Only the **16 entities and their relationships** are left, so the database and the ERD are exactly the same as the real app, but nothing works yet.

Use it to:

- Study how the tables connect (`@ManyToOne`, `@OneToOne`, `@JoinColumn`).
- Reverse engineer the full ERD in MySQL Workbench.
- Practice building a feature from scratch: repository → service → controller.

## What was removed

| Removed | Still on `main` |
|---|---|
| Controllers (every `/api/...` endpoint) | `*/controller` |
| Services and rules (reviews, student ID checks, energy, rank points, commission) | `*/service` |
| Repositories, DTOs, the payment gateway | `*/repository`, `*/dto`, `transaction/gateway` |
| Logic inside entities (`approve()`, `reject()`, `addPoints()`), the `Rank` enum, the reward ranges in `QuestSize` | `*/entity` |
| Sample data (`DevDataSeeder`), error handling, health check | `common/` |
| Unit tests | `src/test` |
| The React frontend and the old HTML pages | `frontend/`, `src/main/resources/static` |

Kept: every entity's fields, relationships, column names, unique constraints and enums, plus Lombok `@Getter`/`@Setter` (not features, just less typing).

## Tables and relationships

| Objective | Table | Foreign keys |
|---|---|---|
| 1. Account | `users` | none (most tables point here) |
| | `profiles` | `user_id` → users (one-to-one) |
| | `student_verifications` | `user_id` → users, `reviewed_by` → users |
| 2. Quests | `categories` | none |
| | `quests` | `poster_id` → users, `category_id` → categories, `reposted_from_id` → quests (self) |
| | `quest_photos` | `quest_id` → quests |
| 3. Discovery | `saved_quests` | `user_id` → users, `quest_id` → quests |
| | `category_follows` | `user_id` → users, `category_id` → categories |
| | `notifications` | `user_id` → users, `quest_id` → quests |
| 4. Transaction | `quest_acceptances` | `quest_id` → quests, `adventurer_id` → users |
| | `payments` | `payer_id` → users, `acceptance_id` → quest_acceptances |
| | `energy_transactions` | `user_id` → users |
| 5. Community | `guild_ranks` | `user_id` → users (one-to-one) |
| | `messages` | `quest_id` → quests, `sender_id` → users |
| | `reviews` | `acceptance_id` → quest_acceptances, `reviewer_id` → users |
| 5. Moderation | `reports` | `reporter_id` → users, `resolved_by` → users |

16 tables, 25 foreign keys.

## Run it

Needs JDK 25. In IntelliJ: open the project folder and click ▶ on `SidequestSApplication`. Or in a terminal:

```powershell
.\mvnw.cmd spring-boot:run            # H2 in memory, console at http://localhost:8080/h2-console
```

### See the ERD in MySQL Workbench

Use a separate schema so it never mixes with the real app's `sidequest` schema:

```powershell
$env:MYSQL_PASSWORD="your MySQL root password"   # never put it in a file
$env:MYSQL_DB="sidequest_skeleton"
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=mysql"
```

Then **Database → Reverse Engineer**, tick `sidequest_skeleton`, and finish.
