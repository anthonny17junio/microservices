# Item Microservice

A Spring Boot microservice that consumes the **Product Microservice** to manage items. An "Item" consists of a Product plus a randomly generated quantity for testing purposes.

## Prerequisites

* **Java 17** or higher.
* **Product Microservice** must be running at `http://localhost:8001`.

## Configuration

### Port Configuration
By default, this application runs on **port 8002**. You can change this in `src/main/resources/application.properties` using the `server.port` property.

## API Endpoints

| Method | Endpoint | Description                                                            |
| :--- | :--- |:-----------------------------------------------------------------------|
| **GET** | `http://localhost:8002` | List all items (retrieves all products and assigns random quantities). |
| **GET** | `http://localhost:8002/{id}` | Retrieve a specific item by Product ID (with a random quantity).       |

## Data Model (Item)
The response follows this structure:
```json
{
  "product": {
    "id": 1,
    "name": "Smartphone",
    "price": 599.99,
    "createdAt": "2026-01-30"
  },
  "quantity": 5
}
```
*Note: The `quantity` field is generated randomly for testing.*