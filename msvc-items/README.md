# Item Microservice

A Spring Boot microservice that consumes the **Product Microservice** to manage items.
An item consists of a product plus a randomly generated quantity for testing purposes.

This service uses **Spring Cloud LoadBalancer** with **Simple Discovery Client** to perform
**client-side load balancing** across multiple instances of the Product Microservice.

---

## Prerequisites

- Java 17 or higher
- Product Microservice running on ports **8001** and **9001**

---

## Load Balancing Strategy

This microservice uses **Spring Cloud LoadBalancer** for client-side load balancing.

### Dependency Used

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
```

### Service Discovery Configuration

Instead of using a service registry (Eureka, Consul, etc.), this project uses
**Spring Cloud Simple Discovery Client**, where service instances are defined manually.

Configured in:

`src/main/resources/application.properties`

```properties
spring.cloud.discovery.client.simple.instances.msvc-products[0].uri=http://localhost:8001
spring.cloud.discovery.client.simple.instances.msvc-products[1].uri=http://localhost:9001
```

Spring Cloud LoadBalancer automatically distributes requests between these instances.

---

## Running the Application

| Property     | Value |
|--------------|-------|
| Default port | 8002  |

---

## API Endpoints

| Method | Endpoint                   | Description                 |
|--------|----------------------------|-----------------------------|
| GET    | http://localhost:8002      | List all items              |
| GET    | http://localhost:8002/{id} | Retrieve item by product ID |

---

## Response Example

```json
{
  "product": {
    "id": 1,
    "name": "Smartphone",
    "price": 599.99,
    "createdAt": "2026-01-30",
    "port": 8001
  },
  "quantity": 5
}
```

*The quantity value is generated randomly for testing.*
