# Eureka Service Discovery Server

This project acts as a **Service Registry** using **Spring Cloud Netflix Eureka**. It provides a central place where all
microservices in the ecosystem can register themselves, enabling dynamic service discovery and communication.

---

## Prerequisites

* **Java 17** or higher.
* Maven (or the provided `./mvnw` wrapper).

## Core Configuration

The application is annotated with `@EnableEurekaServer` in the main class. Since this project is the **central server**
and not a client microservice, it is configured to avoid self-registration.

## Key Features

* **Service Registry**: Maintains a dynamic list of all available microservice instances.
* **Health Checks**: Monitors the status of instances via heartbeats.
* **Client-Side Load Balancing Support**: Provides the necessary metadata for clients to perform load balancing.

---

## Running the Server

1. **Build the project**:
   ```bash
   ./mvnw clean install

2. **Run the application**:
   ```bash
   ./mvnw spring-boot:run

---

## Eureka Dashboard

Once the server is running, you can access the built-in web dashboard to monitor the status, IP addresses, and ports of
all registered microservices.

👉 URL: http://localhost:8761/