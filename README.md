# MedConsult Back

REST API backend for **MedConsult**, built with Spring Boot. It provides JWT-based user authentication (register/login) and a foundation for protected endpoints.

## Tech stack

- Java 17
- Spring Boot 3.4 (Web, Security, Data JPA, Validation)
- PostgreSQL
- JWT (`jjwt`)
- SpringDoc OpenAPI (Swagger UI)

## Prerequisites

- JDK 17+
- PostgreSQL running locally (or reachable remotely)
- Gradle (wrapper included: `./gradlew` / `gradlew.bat`)

## Getting started

### 1. Create the database

```sql
CREATE DATABASE medconsult;
```

### 2. Configure the application

Defaults live in `src/main/resources/application.yml`:

| Setting | Default |
|---------|---------|
| Server port | `8080` |
| Database URL | `jdbc:postgresql://localhost:5432/medconsult` |
| Database user | `postgres` |
| Database password | `1234` |

Override these for your environment by editing `application.yml` or using environment variables / Spring profiles.

**JWT secret** (Base64-encoded key for HS256):

```bash
# Optional — uses the dev default from application.yml if unset
export JWT_SECRET=your-base64-encoded-secret
```

On Windows (PowerShell):

```powershell
$env:JWT_SECRET = "your-base64-encoded-secret"
```

### 3. Run the application

```bash
./gradlew bootRun
```

Windows:

```powershell
.\gradlew.bat bootRun
```

The API starts at `http://localhost:8080`.

## API documentation

Interactive docs are available at:

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

Use the **Authorize** button in Swagger UI and enter `Bearer <your-jwt-token>` to call protected endpoints.

## Authentication

All routes except `/auth/**` and Swagger require a valid JWT in the `Authorization` header:

```
Authorization: Bearer <token>
```

### Register

```http
POST /auth/register
Content-Type: application/json

{
  "username": "john.doe@example.com",
  "password": "SecurePass123@",
  "firstName": "John",
  "lastName": "Doe",
  "country": "Colombia"
}
```

Password rules: minimum 8 characters, at least one uppercase letter, lowercase letter, number, and special character (`@#$%^&+=`).

### Login

```http
POST /auth/login
Content-Type: application/json

{
  "username": "john.doe@example.com",
  "password": "SecurePass123@"
}
```

Both endpoints return:

```json
{
  "token": "<jwt>",
  "user": {
    "id": 1,
    "username": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "country": "Colombia",
    "role": "USER"
  }
}
```

### Protected example

```http
POST /api/v1/demo
Authorization: Bearer <token>
```

## Project structure

```
src/main/java/com/hero/medconsult/back/
├── MedconsultBackApplication.java   # Entry point
├── auth/                            # Auth controller & request/response DTOs
├── config/                          # Security, CORS, OpenAPI, auth beans
├── demo/                            # Sample protected controller
├── dto/                             # API response DTOs
├── exception/                       # Global exception handling
├── jwt/                             # JWT generation & request filter
├── mapper/                          # Entity ↔ DTO mappers
├── model/                           # JPA entities (User, Role)
├── repository/                      # Spring Data repositories
└── service/                         # Business logic
```

## Running tests

```bash
./gradlew test
```

## Security notes

- Change the JWT secret and database credentials before deploying to production.
- Hibernate `ddl-auto: update` is convenient for development; use migrations (e.g. Flyway/Liquibase) for production.
- CORS is configured permissively for local frontend development (`config/CorsConfig.java`).

## License

Apache 2.0 (see OpenAPI config for project metadata).
