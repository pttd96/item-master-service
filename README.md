# item-master-service
Item Master Service

## Run (Java 17)

Use Java 17 and Gradle wrapper to run the app:

```bash
cd /workspaces/item-master-service
JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 PATH=/usr/lib/jvm/java-17-openjdk-amd64/bin:$PATH ./gradlew clean bootRun -x test
```

## Run with Docker

Prerequisites: Docker and Docker Compose installed.

To build and run the app in a container:

```bash
docker compose up --build
```

The app will be available at `http://localhost:8080`.

To stop the containers:

```bash
docker compose down
```

## Endpoints

### Item APIs
- `GET /items`
- `GET /items/{id}`
- `POST /items` (JSON body: `{ "itemClass":"...", "subclass":"...", "department":"...", "price":123.45, "buyerId":1 }`)
- `PUT /items/{id}` (same JSON shape)
- `DELETE /items/{id}`

### Buyer APIs
- `GET /buyers`
- `GET /buyers/{id}`
- `POST /buyers` (JSON body: `{ "name": "...", "email": "..." }`)
- `PUT /buyers/{id}` (same JSON shape)
- `DELETE /buyers/{id}`

### Validation APIs
- `GET /validations`
- `GET /validations/{id}`
- `POST /validations` (JSON body: `{ "itemClass":"...", "subclass":"...", "department":"...", "maxThreshold":100.0, "minThreshold":1.0, "isActive":true }`)

## H2 Console

Open `http://localhost:8080/h2-console`, JDBC URL: `jdbc:h2:mem:items`.

