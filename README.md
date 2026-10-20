# SideQuest/s

A gig marketplace for CIT-U students. Students can offer small services (tutoring, design, errands, and so on) as listings, other students can find and book them, and both sides can review each other after a meetup.

> **Status:** starter skeleton. The entities, repositories and read-only placeholder endpoints are in place, but the real features (registration, booking flow, payments, moderation) are still TODO.

## Tech stack

| Layer      | Choice                                                   |
|------------|----------------------------------------------------------|
| Language   | Java 25 (LTS)                                            |
| Framework  | Spring Boot 3.5.x (Web, Data JPA, Validation, DevTools)  |
| Build      | Maven (wrapper included: `mvnw` / `mvnw.cmd`)            |
| Database   | H2 in-memory (dev), MySQL (later)                        |
| Other      | Lombok                                                   |
| Security   | None yet (TODO: add Spring Security + BCrypt)            |

## How to run

Requirements: JDK 25 (LTS; set `JAVA_HOME` to it). Maven itself is not needed because the wrapper downloads it.

```bash
# Windows (PowerShell / cmd)
mvnw.cmd spring-boot:run

# macOS / Linux / Git Bash
./mvnw spring-boot:run
```

The app starts on <http://localhost:8080> with the `dev` profile, which uses H2 and loads sample data.

| URL                                         | What it shows                      |
|---------------------------------------------|------------------------------------|
| `GET /api/health`                           | `{"status":"ok","app":"SideQuest/s"}` |
| `GET /api/users`                            | Users (no password hashes)         |
| `GET /api/listings`, `GET /api/categories`  | Listings and categories            |
| `GET /api/search?q=java`, `?categoryId=1`   | Placeholder search (active listings) |
| `GET /api/bookmarks`                        | Bookmarks                          |
| `GET /api/bookings`                         | Bookings                           |
| `GET /api/reviews`, `GET /api/posts`        | Reviews, pitch wall posts          |
| `GET /api/reports`                          | Reports (admin)                    |
| `/h2-console`                               | H2 web console                     |

H2 console login: JDBC URL `jdbc:h2:mem:sidequest`, user `sa`, empty password.

### Sample data (dev profile only)

`DevDataSeeder` inserts 2 students, 1 admin (each with a profile), 1 category and 1 active listing. The password hash is the placeholder `CHANGE_ME`. These are not real accounts.

### Switching to MySQL later

1. Create a database named `sidequest`.
2. Uncomment the MySQL block in `src/main/resources/application.properties`.
3. Set the environment variables `DB_HOST`, `DB_PORT`, `DB_USERNAME`, `DB_PASSWORD` (or use a local `.env`, which is git-ignored). Never commit real passwords.

## Package layout

Base package `com.sidequest`, grouped by objective. Each feature package has the same sub-folders:

```
com.sidequest
├── account       entity/ repository/ service/ controller/ dto/
├── listing       entity/ repository/ service/ controller/ dto/
├── discovery     entity/ repository/ service/ controller/ dto/
├── transaction   entity/ repository/ service/ controller/ dto/
├── community     entity/ repository/ service/ controller/ dto/
├── admin         entity/ repository/ service/ controller/ dto/
└── common
    ├── config    DevDataSeeder
    └── web       HealthController, GlobalExceptionHandler
```

Conventions:

- Controllers return DTOs (Java records), never entities. `passwordHash` is never exposed.
- All relationships are `LAZY`, and `spring.jpa.open-in-view=false`, so DTOs are built inside `@Transactional` service methods.
- Status fields are enums: `Role`, `ListingStatus`, `BookingStatus`, `PaymentStatus`, `ReportStatus`.

## Packages and objectives

| Package       | Objective                       | Entities / endpoints                                          |
|---------------|---------------------------------|---------------------------------------------------------------|
| `account`     | Objective 1: Account            | `User`, `Profile`, `/api/users`                               |
| `listing`     | Objective 2: Listing            | `Listing`, `ListingPhoto`, `Category`, `/api/listings`, `/api/categories` |
| `discovery`   | Objective 3: Discovery          | `Bookmark`, `/api/bookmarks`, `/api/search`                   |
| `transaction` | Objective 4: Transaction        | `Booking`, `Payment`, `Notification`, `/api/bookings`         |
| `community`   | Objective 5: Community and Admin | `Review`, `Message`, `/api/reviews`; extra (pitch wall): `Post`, `Comment`, `/api/posts` |
| `admin`       | Objective 5: Community and Admin | `Report`, `/api/reports`                                     |
| `common`      | Shared                          | Health endpoint, exception handler, dev seed data             |

## TODO

- Spring Security (login, roles, BCrypt password hashing, admin-only endpoints)
- Create/update endpoints with request DTOs and validation
- Real search filters, pagination
- Booking, payment and notification flows
- Tests
