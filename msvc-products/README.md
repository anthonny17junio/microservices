# Product Microservice

A Spring Boot microservice for managing product data.

> ⚠️ **Important**: This microservice is now intended to be run in **multiple instances** to test **client-side load
balancing** with `msvc-items`.

## Prerequisites

* **Java 17** or higher.
* **PostgreSQL** running on port `5432`.

## Running the Application (Multiple Instances)

To test **load balancing**, this microservice must be started **twice**, each time on a different port:

* **Instance 1:** Port `8001`
* **Instance 2:** Port `9001`

Both instances connect to the **same database**.

---

## Port Configuration Using VM Options (Required)

Instead of hardcoding the port in `application.properties`, you must pass it as a **VM option** when starting the
application.

### Example

**Run instance on port 8001**

```
-Dserver.port=8001
```

**Run instance on port 9001**

```
-Dserver.port=9001
```

---

## Database Credentials

You can provide the database credentials using one of the following two methods:

### 1. Command Line Arguments (Recommended)

Pass the credentials directly when running the application. This method keeps your credentials out of the source code:

```bash
--DB_USER=your_username --DB_PASSWORD=your_password
```

### 2. Application Properties

Alternatively, you can edit the credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/db_springboot_cloud
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## API Endpoints

| Method | Endpoint                     | Description                 |
|--------|------------------------------|-----------------------------|
| GET    | http://localhost:{port}      | List all available products |
| GET    | http://localhost:{port}/{id} | Retrieve product by ID      |
