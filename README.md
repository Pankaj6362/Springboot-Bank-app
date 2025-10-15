# Bank App (Simple, no JWT)

This is a simple Spring Boot banking application with signup/login (simple session tokens), deposit, withdraw, and transaction history.
The app uses MySQL and comes with a Dockerfile and docker-compose.yml to run both app and DB.

## Quick start with Docker Compose (recommended)

1. Build & run:
   ```bash
   docker-compose up --build
   ```
2. Sign up:
   ```bash
   curl -X POST http://localhost:8080/api/auth/signup -H "Content-Type: application/json" -d '{"email":"alice@example.com","password":"secret","fullName":"Alice"}'
   ```
   Response will include a `token` — a simple session token. For subsequent requests include header `X-Session-Token: <token>`.

3. Example deposit:
   ```bash
   TOKEN=the-token-from-signup
   curl -X POST http://localhost:8080/api/account/deposit -H "X-Session-Token: $TOKEN" -H "Content-Type: application/json" -d '{"amount":100.00,"description":"Initial"}'
   ```

## Build jar locally
Requires JDK and Maven:
```bash
mvn -B clean package
ls target/*.jar
```

