# ChâTop API

Back-end of ChâTop, a seasonal rental portal: user accounts, rentals with pictures, and messages to the owners of the rentals. It replaces the Mockoon environment of the [Angular front-end provided by OpenClassrooms](https://github.com/OpenClassrooms-Student-Center/Mod-lisez-et-impl-mentez-le-back-end-en-utilisant-du-code-Java-maintenable), which is used unchanged.

## Technologies

- Java 17, Spring Boot 4.1
- Spring Security with JSON Web Tokens (OAuth2 Resource Server)
- Spring Data JPA (Hibernate) and MySQL 8
- springdoc-openapi (Swagger UI)
- Maven, through the included wrapper

## Prerequisites

- JDK 17 or later
- MySQL 8
- Node.js 22, only to run the front-end

## Installation

### 1. Database

Create the database, its user and its tables with [`docs/database.sql`](docs/database.sql), as described in [`docs/database.md`](docs/database.md).

### 2. Configuration

Copy `.env.example` to `.env`, at the root of the project, and fill it in:

| Variable | Value |
|---|---|
| `DB_URL` | JDBC URL of the database, `jdbc:mysql://localhost:3306/chatop_db` by default |
| `DB_USERNAME` | database user, `chatop_user` by default |
| `DB_PASSWORD` | password chosen when running `docs/database.sql` |
| `JWT_SECRET` | random secret of at least 32 characters that signs the tokens, for example generated with `openssl rand -base64 32` |

Git ignores `.env`, so the credentials never leave your machine. These variables can also be set as environment variables.

### 3. Launch

```bash
./mvnw spring-boot:run
```

On Windows, use `mvnw.cmd` instead of `./mvnw`. The API starts on http://localhost:3001.

## Use with the front-end

Clone [the front-end provided by OpenClassrooms](https://github.com/OpenClassrooms-Student-Center/Mod-lisez-et-impl-mentez-le-back-end-en-utilisant-du-code-Java-maintenable), then install its dependencies with `npm install --no-package-lock`: its `package-lock.json` points to a private registry. Start it with `npm start`, and open http://localhost:4200. Its development server sends the `/api` requests to the API on port 3001, so Mockoon is not needed.

## Documentation

- **Swagger UI:** http://localhost:3001/swagger-ui.html, once the API is running. Log in with `POST /api/auth/login`, then paste the token in **Authorize** to try the protected endpoints.
- **API definition:** [`docs/api-definition.md`](docs/api-definition.md) describes the endpoints, the data objects and the status codes.
- **Database:** [`docs/database.md`](docs/database.md) describes the schema and its limitations.

## Other settings

They are in [`src/main/resources/application.properties`](src/main/resources/application.properties):

| Property | Default | Purpose |
|---|---|---|
| `server.port` | `3001` | port expected by the front-end proxy |
| `jwt.expiration` | `24h` | lifetime of the tokens |
| `images.directory` | `uploads` | folder of the rental pictures, created at the first upload and ignored by Git |
| `images.base-url` | `http://localhost:3001/api/images` | start of the picture URLs saved with each new rental |

## Tests

```bash
./mvnw verify
```

The test starts the whole application: MySQL must be running and `.env` must be filled in.

## Project structure

| Package | Content |
|---|---|
| `config` | security, JWT, pictures and Swagger settings |
| `controller` | REST endpoints |
| `dto` | request and response bodies |
| `entity` | JPA entities |
| `exception` | business exceptions and JSON error responses |
| `repository` | Spring Data repositories |
| `service` | business logic |
