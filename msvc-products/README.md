# Product Microservice

A Spring Boot microservice for managing product data.

## Prerequisites

* **Java 17** or higher.
* **PostgreSQL** running on port `5432`.

## Database Setup

1. **Create the database**:
   ```sql
   CREATE DATABASE db_springboot_cloud;
   ```

2. **Create the products table**:
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

## Running the Application

### Port Configuration
By default, the application runs on **port 8001**. You can change this in `src/main/resources/application.properties` using the `server.port` property.

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

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **GET** | `http://localhost:8001` | List all available products. |
| **GET** | `http://localhost:8001/{id}` | Retrieve a specific product by its ID. |

## Optional Test Data
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