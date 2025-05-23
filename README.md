# Inventory Management System

A Spring Boot application for inventory management with role-based access control using Spring Security.

## Features

- User authentication with JWT
- Role-based access control (ADMIN and MANAGER roles)
- Product management
- Category management
- Supplier management
- PostgreSQL database

## Requirements

- Java 17
- Maven 3.6+
- PostgreSQL 12+

## Getting Started

### 1. Database Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE inventory_db;
```

### 2. Configure Database Connection

Update `src/main/resources/application.yml` with your database credentials:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/inventory_db
    username: your_username
    password: your_password
```

### 3. Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on port 8080 by default.

## Default Users

The application creates two default users on startup:

| Role    | Email                   | Password    | Access Level                              |
|---------|-------------------------|-------------|-------------------------------------------|
| ADMIN   | admin@inventory.com     | admin123    | Full access to all endpoints              |
| MANAGER | manager@inventory.com   | manager123  | Limited access (no user management)       |

## API Endpoints

### Authentication

#### Login
```http
POST /auth/login
Content-Type: application/json

{
  "email": "admin@inventory.com",
  "password": "admin123"
}
```

**Expected Response:**
```json
"Login successful"
```

#### Register New User (Admin Only)
```http
POST /auth/register
Content-Type: application/json

{
  "email": "newuser@inventory.com",
  "password": "password123",
  "fullName": "New User",
  "roleName": "MANAGER"
}
```

**Expected Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "email": "newuser@inventory.com",
  "fullName": "New User",
  "status": "ACTIVE",
  "role": {
    "id": "550e8400-e29b-41d4-a716-446655440001",
    "name": "MANAGER",
    "description": "Manager with limited access"
  },
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### Categories (Admin and Manager Access)

#### Create Category
```http
POST /api/categories
Content-Type: application/json
Authorization: Basic admin@inventory.com:admin123

{
  "name": "Electronics",
  "description": "Electronic devices and accessories"
}
```

**Expected Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440002",
  "name": "Electronics",
  "description": "Electronic devices and accessories"
}
```

#### Get All Categories
```http
GET /api/categories
Authorization: Basic admin@inventory.com:admin123
```

**Expected Response:**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440002",
    "name": "Electronics",
    "description": "Electronic devices and accessories"
  }
]
```

### Suppliers (Admin and Manager Access)

#### Create Supplier
```http
POST /api/suppliers
Content-Type: application/json
Authorization: Basic manager@inventory.com:manager123

{
  "name": "Tech Supplies Inc.",
  "address": "123 Tech Street, Silicon Valley, CA 94000",
  "companyInfo": "Leading technology supplier since 2000",
  "email": "contact@techsupplies.com",
  "phone": "4081234567"
}
```

**Expected Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440003",
  "name": "Tech Supplies Inc.",
  "address": "123 Tech Street, Silicon Valley, CA 94000",
  "companyInfo": "Leading technology supplier since 2000",
  "email": "contact@techsupplies.com",
  "phone": "4081234567"
}
```

### Products (Admin and Manager Access)

#### Create Product
```http
POST /api/products
Content-Type: application/json
Authorization: Basic manager@inventory.com:manager123

{
  "name": "Laptop Pro 15",
  "description": "High-performance laptop with 16GB RAM",
  "availableQuantity": 50,
  "unitPrice": 1299.99,
  "categoryId": "550e8400-e29b-41d4-a716-446655440002",
  "supplierId": "550e8400-e29b-41d4-a716-446655440003"
}
```

**Expected Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440004",
  "name": "Laptop Pro 15",
  "description": "High-performance laptop with 16GB RAM",
  "availableQuantity": 50,
  "unitPrice": 1299.99,
  "category": {
    "id": "550e8400-e29b-41d4-a716-446655440002",
    "name": "Electronics",
    "description": "Electronic devices and accessories"
  },
  "supplier": {
    "id": "550e8400-e29b-41d4-a716-446655440003",
    "name": "Tech Supplies Inc.",
    "address": "123 Tech Street, Silicon Valley, CA 94000",
    "companyInfo": "Leading technology supplier since 2000",
    "email": "contact@techsupplies.com",
    "phone": "4081234567"
  },
  "createdAt": "2024-01-15T10:35:00",
  "updatedAt": "2024-01-15T10:35:00"
}
```

#### Update Product
```http
PUT /api/products/550e8400-e29b-41d4-a716-446655440004
Content-Type: application/json
Authorization: Basic admin@inventory.com:admin123

{
  "name": "Laptop Pro 15 - Updated",
  "description": "High-performance laptop with 32GB RAM",
  "availableQuantity": 45,
  "unitPrice": 1499.99,
  "categoryId": "550e8400-e29b-41d4-a716-446655440002",
  "supplierId": "550e8400-e29b-41d4-a716-446655440003"
}
```

### Users (Admin Only)

#### Get All Users
```http
GET /api/users
Authorization: Basic admin@inventory.com:admin123
```

**Expected Response:**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440005",
    "email": "admin@inventory.com",
    "fullName": "System Administrator",
    "status": "ACTIVE",
    "role": {
      "id": "550e8400-e29b-41d4-a716-446655440006",
      "name": "ADMIN",
      "description": "System Administrator with full access"
    },
    "createdAt": "2024-01-15T10:00:00",
    "updatedAt": "2024-01-15T10:00:00"
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440007",
    "email": "manager@inventory.com",
    "fullName": "Inventory Manager",
    "status": "ACTIVE",
    "role": {
      "id": "550e8400-e29b-41d4-a716-446655440008",
      "name": "MANAGER",
      "description": "Manager with limited access"
    },
    "createdAt": "2024-01-15T10:00:00",
    "updatedAt": "2024-01-15T10:00:00"
  }
]
```

## Testing with cURL

### Login as Admin
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@inventory.com","password":"admin123"}'
```

### Create a Category (as Manager)
```bash
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -u "manager@inventory.com:manager123" \
  -d '{"name":"Books","description":"Books and publications"}'
```

### Create a Supplier (as Admin)
```bash
curl -X POST http://localhost:8080/api/suppliers \
  -H "Content-Type: application/json" \
  -u "admin@inventory.com:admin123" \
  -d '{"name":"Book World","address":"456 Book Ave","companyInfo":"Major book distributor","email":"info@bookworld.com","phone":"5551234567"}'
```

### Get All Products
```bash
curl -X GET http://localhost:8080/api/products \
  -u "manager@inventory.com:manager123"
```

## Error Responses

### Unauthorized Access
```json
{
  "timestamp": "2024-01-15T10:40:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Full authentication is required to access this resource",
  "path": "/api/users"
}
```

### Forbidden Access (Manager trying to access admin-only endpoint)
```json
{
  "timestamp": "2024-01-15T10:41:00",
  "status": 403,
  "error": "Forbidden",
  "message": "Access is denied",
  "path": "/api/users"
}
```

### Not Found
```json
{
  "timestamp": "2024-01-15T10:42:00",
  "status": 404,
  "error": "Not Found",
  "message": "Product not found",
  "path": "/api/products/invalid-uuid"
}
```

## Troubleshooting

1. **Database Connection Issues**
   - Ensure PostgreSQL is running
   - Verify database credentials in application.yml
   - Check if the database 'inventory_db' exists

2. **Authentication Issues**
   - Ensure you're using the correct credentials
   - Check if the user status is "ACTIVE"
   - Verify the role name is correct

3. **Build Issues**
   - Ensure Java 17 is installed and configured
   - Run `mvn clean install` to resolve dependencies
   - Check for compilation errors in the console

## License

This project is licensed under the MIT License.