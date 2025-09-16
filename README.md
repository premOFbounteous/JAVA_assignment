# OrderUp — JAVA_Assignment

This is a Spring Boot application for processing orders and inventory for custom handcrafted products. It handles placing orders, reserving stock, and exposing APIs for products and orders. The application is built with proper error handling, transaction safety, and tests ensuring high code coverage.

---

## 🚀 Features

- CRUD-like APIs for **Products**  
  - Get all products  
  - Get product by ID  
  - Create a new product  

- API to **place orders**  
  - Validates product exists  
  - Checks sufficient stock  
  - Deducts stock, creates order record with status  

- API to **list all orders**

- Error handling via exceptions:  
  - `ProductNotFoundException` if product ID is invalid  
  - `InsufficientStockException` if stock < requested quantity  

- Logging using AOP for measuring execution time of key operations  

---

## 🧱 Project Structure

| Layer | Description |
|-------|-------------|
| `model/` | Entities: `Product`, `Order` |
| `repository/` | Data access layer (interfaces extending Spring Data JPA) |
| `service/` | Business logic: product operations, placing orders, stock checks |
| `controller/` | REST API layer: product endpoints, order endpoints |
| `exception/` | Custom exceptions + global exception handler |
| `aop/` | Aspect for logging execution time |
| `dto/` | Data Transfer Objects (e.g. OrderRequest) |
| `test/` | Unit & Web tests ensuring correctness and coverage |

---

## ⚙️ Database Schema

```sql
-- Products table
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    stock INT NOT NULL
);

-- Orders table
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    product_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES products(id)
);
