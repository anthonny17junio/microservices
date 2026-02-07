# Item Microservice

A Spring Boot microservice that consumes the **Product Microservice**. It uses **Netflix Eureka
** for dynamic service discovery.
---

## Prerequisites

- Java 17 or higher
- Product Microservice running on ports **8001** and **9001**

---

## Load Balancing & Client Configuration

This microservice implements two communication strategies: **Feign** and **WebClient**. Currently, **WebClient** is the
active implementation (`@Primary`).

### 1. Dependencies Used

### Dependency Used

```xml

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

### 2. WebClient Configuration

To enable load balancing with `WebClient`, we use a configuration class that injects the `@LoadBalanced` interceptor
into the `WebClient.Builder`. This allows the client to resolve the service name `msvc-products` using the instances
defined in the discovery client.

```java

@Configuration
public class WebClientConfig {
    @Value("${config.baseurl.endpoint.msvc-products}")
    private String baseUrl;

    @Bean
    @LoadBalanced
    WebClient.Builder webClient() {
        return WebClient.builder().baseUrl(this.baseUrl);
    }
}
```

### 3. Service Discovery (Eureka)

Configured in `src/main/resources/application.properties`:

```properties
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
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
