# Item Master Service

A Spring Boot-based REST API service for managing items, buyers, and validation rules in an e-commerce or retail system. The service provides comprehensive CRUD operations for items and buyers, along with a sophisticated price update approval workflow.

## Features

- **Item Management**: Full CRUD operations for items with classification (item class, subclass, department)
- **Buyer Management**: CRUD operations for buyer information
- **Validation Rules**: Configurable price change thresholds by item classification
- **Price Approval Workflow**: Automatic approval/rejection of price updates based on validation rules
- **H2 Database**: In-memory database for development and testing
- **Docker Support**: Containerized deployment with Docker Compose

## Requirements

### Main Flow: Price Update Approval System

When an item's price is updated with a new value:
- **Within Threshold**: If the new price is within the configured validation threshold for that item's classification, the price is updated immediately and the API returns a success response.
- **Outside Threshold**: If the new price exceeds the allowed threshold, the update API returns a response indicating that approval is required from the buyer of the item. The buyer's email is included in the response for notification purposes.

Validation thresholds are defined per item classification (item class + subclass + department) and specify minimum and maximum percentage changes allowed for price updates.

## Endpoints

### Item APIs
- `GET /items` - Get all items
- `GET /items/{id}` - Get item by ID
- `POST /items` - Create a new item
  - Body: `{ "itemClass":"...", "subclass":"...", "department":"...", "price":123.45, "buyerId":1 }`
- `PUT /items/{id}` - Update an item (supports partial updates)
  - Body: `{ "itemClass":"...", "subclass":"...", "department":"...", "price":123.45, "buyerId":1 }`
  - Returns: Success or pending approval status with buyer email if approval required
- `DELETE /items/{id}` - Delete an item

### Buyer APIs
- `GET /buyers` - Get all buyers
- `GET /buyers/{id}` - Get buyer by ID
- `POST /buyers` - Create a new buyer
  - Body: `{ "name": "...", "email": "..." }`
- `PUT /buyers/{id}` - Update a buyer
- `DELETE /buyers/{id}` - Delete a buyer

### Validation APIs
- `GET /validations` - Get all validation rules
- `GET /validations/{id}` - Get validation rule by ID
- `POST /validations` - Create a new validation rule
  - Body: `{ "itemClass":"...", "subclass":"...", "department":"...", "maxThreshold":100.0, "minThreshold":1.0, "isActive":true }`

## Running with Docker

### Prerequisites
- Docker
- Docker Compose

### Quick Start

1. **Clone the repository**:
   ```bash
   git clone https://github.com/pttd96/item-master-service.git
   cd item-master-service
   ```

2. **Build and run with Docker Compose**:
   ```bash
   docker compose up --build
   ```

3. **Access the application**:
   - API: `http://localhost:8080`
   - H2 Console: `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:mem:items`
     - Username: (leave blank)
     - Password: (leave blank)

4. **Stop the containers**:
   ```bash
   docker compose down
   ```

### Alternative: Run with Java 17

If you prefer to run locally without Docker:

```bash
cd /workspaces/item-master-service
JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 PATH=/usr/lib/jvm/java-17-openjdk-amd64/bin:$PATH ./gradlew clean bootRun -x test
```

## Database

The application uses H2 in-memory database with sample data loaded from `src/main/resources/data.sql`. The database includes:
- Sample items with various classifications
- Sample buyers
- Validation rules for different item categories

## Testing

Run the test suite:

```bash
./gradlew test
```

The project includes:
- Unit tests for all service classes
- Functional tests for API endpoints
- Integration tests with the H2 database

