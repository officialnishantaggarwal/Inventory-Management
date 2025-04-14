# Inventory Management System - Microservices

##### Welcome to the Inventory Management System! This project is designed for beginners stepping into the world of microservices. It provides a simple yet practical implementation of core microservices concepts.

### Features
#### 1. Inventory Service:
- Manage products with full CRUD (Create, Read, Update, Delete) functionality.
- Track and update product quantities.

#### 2. Order Service:
- Create and cancel orders.
- Automatically adjust product quantities based on order actions (reduce on order creation, restore on cancellation).

#### 3. Shipping Service:
- Confirm and cancel shipping.
- Track and update the Shipping status and other info.

#### 4. Service Discovery:
- Centralized service registry using **Netflix Eureka** for service discovery and registration.

#### 5. API Gateway:
- Single entry point for all microservices.
- Simplifies and centralizes request routing.

#### 6. Inter-service Communication:
- Seamless communication between services using **OpenFeign clients**.

#### 7. Fault Tolerance:
- Implemented **Resilience4j** for circuit breaker, retry mechanisms, and resilience in microservice communication.

#### 8. Config-Server:
- Implemeted Centralized Configuration Server using Github
- Integrated Spring Cloud Config Server for managing service configurations.
- Externalized configurations stored on GitHub for versioning and maintainability.

#### 9. Distributed Request Tracing:
- Distributed tracing using zipkin & micrometer
- Trace request flow across services using Zipkin and Micrometer.
- Gain deep observability into service interactions and performance bottlenecks.

#### 10. ELK Centralized Logging:
- Centralized logging with the ELK Stack
- Unified logging across services
- Real-time log analysis and visualization


### Tech Stack
- **Backend**: Spring Boot (Microservices)
- **Service Registry**: Netflix Eureka Server
- **Database**: MySQL
- **Inter-service Communication**: OpenFeign Client
- **Fault Tolerance**: Resilience4j
- **API Gateway**: Spring Cloud Gateway
- **Tracing**: Zipkin & Micrometer
- **Logging**: ELK (Elastic Logstash Kibana) Stack
