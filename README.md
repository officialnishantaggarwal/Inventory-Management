# Inventory Management System - Microservices

##### Welcome to the Inventory Management System! This project is designed for beginners stepping into the world of microservices. It provides a simple yet practical implementation of core microservices concepts.

### Features
#### 1. Inventory Service:
- Manage products with full CRUD (Create, Read, Update, Delete) functionality.
- Track and update product quantities.

#### 2. Order Service:
- Create and cancel orders.
- Automatically adjust product quantities based on order actions (reduce on order creation, restore on cancellation).

#### 2. Order Service:
- Create and cancel orders.
- Automatically adjust product quantities based on order actions (reduce on order creation, restore on cancellation).

#### 3. Service Discovery:
- Centralized service registry using **Netflix Eureka** for service discovery and registration.

#### 4. API Gateway:
- Single entry point for all microservices.
- Simplifies and centralizes request routing.

#### 5. Inter-service Communication:
- Seamless communication between services using **OpenFeign clients**.

#### 6. Fault Tolerance:
- Implemented **Resilience4j** for circuit breaker, retry mechanisms, and resilience in microservice communication.


### Tech Stack
- **Backend**: Spring Boot (Microservices)
- **Service Registry**: Netflix Eureka Server
- **Database**: MySQL
- **Inter-service Communication**: OpenFeign Client
- **Fault Tolerance**: Resilience4j
- **API Gateway**: Spring Cloud Gateway
