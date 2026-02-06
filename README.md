# Microservices with Spring Boot & Spring Cloud

This repository contains a set of microservices built with **Spring Boot** and **Spring Cloud**, demonstrating a simple
microservices architecture with **client-side load balancing**.

## Projects in this Repository

| Project       | Description                                       | Default Port |
|---------------|---------------------------------------------------|--------------|
| msvc-products | Product management microservice                   | 8001 / 9001  |
| msvc-items    | Item aggregation microservice (consumes products) | 8002         |

---

## Architecture Overview

- `msvc-products` exposes product data and runs in **multiple instances**
- `msvc-items` consumes `msvc-products` using **client-side load balancing**
- Communication is done via REST over HTTP

---

## Running the System

### 1. Start PostgreSQL

Ensure PostgreSQL is running on port `5432` and create the database:

```sql
CREATE
DATABASE db_springboot_cloud;
```

### 2. **Create the products table**:
   Connect to the database and run:
   ```sql
   CREATE TABLE public.products (
       id serial4 NOT NULL,
       name varchar NULL,
       price numeric NULL,
       created_at date NULL,
       CONSTRAINT products_pk PRIMARY KEY (id)
   );
   ```

#### Optional Test Data
You can use these inserts to populate your database for testing:

```sql
INSERT INTO products (name, price, create_at) VALUES('Panasonic', 800, NOW());
INSERT INTO products (name, price, create_at) VALUES('Sony', 700, NOW());
INSERT INTO products (name, price, create_at) VALUES('Apple', 1000, NOW());
INSERT INTO products (name, price, create_at) VALUES('Sony Notebook', 1000, NOW());
INSERT INTO products (name, price, create_at) VALUES('Hewlett Packard', 500, NOW());
INSERT INTO products (name, price, create_at) VALUES('Bianchi', 600, NOW());
INSERT INTO products (name, price, create_at) VALUES('Nike', 100, NOW());
INSERT INTO products (name, price, create_at) VALUES('Adidas', 200, NOW());
INSERT INTO products (name, price, create_at) VALUES('Reebok', 300, NOW());

```

### 3. Start Product Microservice (2 instances)

Run the application twice with different ports:

**Instance 1**

```
-Dserver.port=8001
```

**Instance 2**

```
-Dserver.port=9001
```

### 4. Start Item Microservice

Start `msvc-items` normally (default port `8002`).

---

## Testing

| Service               | URL                   |
|-----------------------|-----------------------|
| Products (instance 1) | http://localhost:8001 |
| Products (instance 2) | http://localhost:9001 |
| Items                 | http://localhost:8002 |
