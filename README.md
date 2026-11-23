# User Management API

A Java 17 and Spring Boot portfolio project for user record management, with a small browser interface and a REST API. The original backend exercise has been reworked into a consistent controller, service and repository structure.

![Desktop demo](docs/desktop.png)

## What it does

- Create, list, read, rename and delete users.
- Normalize unique usernames to lowercase.
- Hash passwords with BCrypt and return only safe response DTOs.
- Validate request fields and return structured 400, 404 and 409 responses.
- Use database persistence through Spring Data JPA.
- Try the interface locally without a database installation using H2 demo mode.

## Stack

Java 17 · Spring Boot 3.5.8 · Spring Web · Spring Data JPA · Jakarta Validation · MySQL · H2 (demo/tests) · Maven Wrapper · JUnit 5 / MockMvc · HTML / CSS / JavaScript

## Quick start: local demo

Install a JDK 17 or later. From this project folder:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=demo
```

Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=demo
```

Open `http://localhost:8080`. Demo mode uses an in-memory H2 database. All demo data is lost when the process stops. The application binds to `127.0.0.1` by default. To run both projects at once, use `PORT=8081` for the second project.

## Use MySQL

1. Create a local database and a dedicated database user. Do not use a privileged administrator account.
2. Use `.env.example` as a reference. The app does **not** automatically read `.env` files. Set environment variables in your terminal or IntelliJ run configuration.
3. Export `DB_URL`, `DB_USERNAME` and `DB_PASSWORD`. Keep credentials out of Git.
4. Start with the `database` profile for a **disposable local database**:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=database
```

The `database` profile uses `ddl-auto=update` to create/update tables for local development. The default profile uses `validate` instead. For a real deployment, add reviewed schema migrations, backups and a supported database-specific integration test setup. Do not point the demo at important data.

The JDBC driver uses `com.mysql.cj.jdbc.Driver`. The example URL targets local MySQL on port 3306.

## API

All request and response bodies use JSON, except successful DELETE responses (204, no body).

| Method | Path | Purpose |
| --- | --- | --- |
| POST | `/api/users` | Create a user; hashes the password with BCrypt |
| GET | `/api/users` | List users without password fields |
| GET | `/api/users/{id}` | Read a user |
| PUT | `/api/users/{id}` | Update the full name |
| DELETE | `/api/users/{id}` | Delete a user |

Example POST body for `/api/users`:

```json
{
  "username": "demo.user",
  "fullName": "Demo User",
  "password": "example-only-123"
}
```

A full-name update accepts `{"fullName":"Updated Name"}`. Usernames and passwords cannot be changed through that endpoint.

Validation failures have a `fields` map. Missing records return 404. Duplicate usernames return 409. Internal exception details are not included in HTTP error bodies.

## Build and test

```bash
./mvnw clean verify
```

The integration suite uses H2 and MockMvc. It covers the main record lifecycle, validation, missing records and duplicate unique fields plus password hashing and response privacy. H2 tests do not prove behavior against MySQL; that database-specific check remains necessary before deployment.

## Structure

```text
src/main/java/com/saad/users/
  *Controller.java       HTTP routes and status codes
  *Service.java          application logic and transactions
  *Repository.java       JPA persistence
  *Dtos.java             validated requests and safe responses
  ApiExceptionHandler    shared error format
src/main/resources/
  application*.properties
  static/                browser demo
src/test/java/           API integration tests
```

## Scope and safety

This is a local portfolio demo, not a production service. It has **no authentication or authorization**, rate limiting, audit trail or pagination. Anyone who can reach it can read and change its demo records. Keep it local and use fictional data. Password hashing is demonstrated, but this is not a sign-in, session or password-reset system. Passwords must contain 8 to 72 characters and no more than 72 UTF-8 bytes.

The revised version includes code repairs, tests, documentation and a browser demo beyond the original exercise. Review the code and be ready to explain it before presenting it as portfolio work. Licensed under the MIT License. See LICENSE.
