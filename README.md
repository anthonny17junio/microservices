# Microservices with Spring Boot & Spring Cloud

This repository contains a set of microservices built with **Spring Boot** and **Spring Cloud**, demonstrating a
microservices architecture with **Service Discovery (Eureka)** and **Client-side Load Balancing**.

## Projects in this Repository

| Project       | Description                                       | Default Port |
|---------------|---------------------------------------------------|--------------|
| eureka-server | Service Discovery Server (Netflix Eureka)         | 8761         |
| msvc-products | Product management microservice                   | 8001 / 9001  |
| msvc-items    | Item aggregation microservice (consumes products) | 8002         |

---

## Architecture Overview

- **Service Discovery**: Managed by Netflix Eureka.
- **Communication**: Handled via **Spring WebClient** (Reactive) and **OpenFeign** (Declarative).
- **Load Balancing**: Dynamic resolution of service names through **Spring Cloud LoadBalancer**.

---

## Running the System (Order Matters)

### 1. Start Infrastructure

#### 1.1. PostgreSQL

Ensure it's running on port `5432` with database `db_springboot_cloud`.
Ensure PostgreSQL is running on port `5432` and create the database:

```sql
CREATE
DATABASE db_springboot_cloud;
```

**Create the products table**:

Connect to the database and run:

   ```sql
   CREATE TABLE public.products
   (
       id         serial4 NOT NULL,
       name       varchar NULL,
       price      numeric NULL,
       created_at date NULL,
       CONSTRAINT products_pk PRIMARY KEY (id)
   );
   ```

**Optional Test Data**:

You can use these inserts to populate your database for testing:

```sql
INSERT INTO products (name, price, create_at)
VALUES ('Panasonic', 800, NOW());
INSERT INTO products (name, price, create_at)
VALUES ('Sony', 700, NOW());
INSERT INTO products (name, price, create_at)
VALUES ('Apple', 1000, NOW());
INSERT INTO products (name, price, create_at)
VALUES ('Sony Notebook', 1000, NOW());
INSERT INTO products (name, price, create_at)
VALUES ('Hewlett Packard', 500, NOW());
INSERT INTO products (name, price, create_at)
VALUES ('Bianchi', 600, NOW());
INSERT INTO products (name, price, create_at)
VALUES ('Nike', 100, NOW());
INSERT INTO products (name, price, create_at)
VALUES ('Adidas', 200, NOW());
INSERT INTO products (name, price, create_at)
VALUES ('Reebok', 300, NOW());
```

#### 1.2. **Eureka Server**: Start the `eureka-server` project on port `8761`. This is the first service to be launched.

### 2. Start Product Microservice (2 instances)

Run the application twice with different ports:

**Instance 1**

```
-Dserver.port=8001
```

**Instance 2**

```
-Dserver.port=9001
```

### 3. Start Item Microservice

Start `msvc-items` (default port `8002`). It will automatically register with Eureka and discover the products service.

---

## Testing & Monitoring

- **Eureka Dashboard**: [http://localhost:8761/](http://localhost:8761/) (Check registered instances here).

| Service               | URL                   |
|-----------------------|-----------------------|
| Products (instance 1) | http://localhost:8001 |
| Products (instance 2) | http://localhost:9001 |
| Items                 | http://localhost:8002 |
