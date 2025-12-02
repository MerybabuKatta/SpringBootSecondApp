# Flight Booking System - Microservices Architecture

A comprehensive flight booking system built with Spring Boot microservices architecture, demonstrating distributed system patterns and best practices.

## 🏗️ Architecture Overview

### Microservices
- **Eureka Server** (Port 8761) - Service Registry & Discovery
- **API Gateway** (Port 8080) - Entry point with Circuit Breaker
- **Search Service** (Port 8081) - Flight search & availability
- **Booking Service** (Port 8082) - Create, modify, cancel bookings
- **Pricing Service** (Port 8083) - Dynamic price calculation
- **Inventory Service** (Port 8084) - Seat management
- **Payment Service** (Port 8085) - Mock payment processing

### Tech Stack
- **Spring Boot 3.4.0** - Microservices framework
- **Spring Cloud 2023.0.0** - Cloud-native patterns
- **Eureka** - Service discovery
- **Spring Cloud Gateway** - API Gateway with load balancing
- **Resilience4j** - Circuit breaker pattern
- **Apache Kafka** - Asynchronous messaging
- **OpenFeign** - Declarative REST clients
- **Spring Data JPA** - Data persistence
- **H2 Database** - In-memory database
- **Lombok** - Boilerplate code reduction

## 🚀 Getting Started

### Prerequisites
- Java 21
- Maven 3.6+
- Apache Kafka (for async messaging)

### Running the System

1. **Start Kafka** (if not already running):
   ```bash
   # Start Zookeeper
   bin/zookeeper-server-start.sh config/zookeeper.properties
   
   # Start Kafka
   bin/kafka-server-start.sh config/server.properties
   ```

2. **Start services in order**:
   ```bash
   # 1. Service Registry
   cd eureka-server && mvn spring-boot:run
   
   # 2. API Gateway
   cd api-gateway && mvn spring-boot:run
   
   # 3. Core Services (can be started in parallel)
   cd search-service && mvn spring-boot:run
   cd booking-service && mvn spring-boot:run
   cd pricing-service && mvn spring-boot:run
   cd inventory-service && mvn spring-boot:run
   cd payment-service && mvn spring-boot:run
   ```

3. **Access the system**:
   - Eureka Dashboard: http://localhost:8761
   - API Gateway: http://localhost:8080
   - H2 Consoles: http://localhost:808X/h2-console (where X is service port)

## 📡 API Endpoints

### Flight Search
```bash
# Search flights
GET /api/flights/search?origin=NYC&destination=LAX&departureDate=2024-12-25T10:00:00

# Get flight details
GET /api/flights/{flightId}
```

### Booking Management
```bash
# Create booking
POST /api/bookings
{
  "flightId": 1,
  "passengerName": "John Doe",
  "passengerEmail": "john@example.com",
  "seats": 2
}

# Confirm booking
PUT /api/bookings/{bookingReference}/confirm

# Cancel booking
PUT /api/bookings/{bookingReference}/cancel

# Get bookings by email
GET /api/bookings?email=john@example.com
```

### Pricing
```bash
# Calculate price
GET /api/pricing/calculate?flightId=1&seats=2
```

### Payment
```bash
# Process payment
POST /api/payments/process
{
  "bookingReference": "BK12345678",
  "amount": 500.00,
  "paymentMethod": "CREDIT_CARD"
}

# Refund payment
POST /api/payments/{paymentId}/refund
```

## 🔧 Key Features

### Distributed System Patterns
- **Service Discovery**: Eureka for automatic service registration
- **API Gateway**: Single entry point with routing and load balancing
- **Circuit Breaker**: Resilience4j for fault tolerance
- **Async Messaging**: Kafka for event-driven communication
- **Centralized Configuration**: Spring Cloud Config ready

### Communication Patterns
- **Synchronous**: REST APIs with OpenFeign clients
- **Asynchronous**: Kafka messaging for booking events
- **Load Balancing**: Client-side load balancing via Eureka

### Data Management
- **Database per Service**: Each microservice has its own H2 database
- **Optimistic Locking**: Inventory service uses versioning for concurrency
- **Event Sourcing**: Booking events trigger inventory updates

## 🎯 Business Logic

### Dynamic Pricing
- Base price from flight data
- Demand-based multipliers:
  - High demand (< 10 seats): 1.5x
  - Medium demand (< 50 seats): 1.2x
  - Normal demand: 1.0x

### Inventory Management
- Real-time seat availability tracking
- Automatic reservation on booking creation
- Automatic release on booking cancellation
- Optimistic locking for concurrent updates

### Payment Processing
- Mock payment gateway with 90% success rate
- Support for refunds
- Transaction tracking

## 🧪 Testing

Each service includes:
- Unit tests with JUnit 5
- Integration tests with TestContainers
- Mock external dependencies with Mockito

```bash
# Run tests for all services
mvn test

# Run tests for specific service
cd search-service && mvn test
```

## 📊 Monitoring & Observability

- **Service Health**: Spring Boot Actuator endpoints
- **Service Registry**: Eureka dashboard for service status
- **Database Console**: H2 console for each service
- **Circuit Breaker**: Resilience4j metrics

## 🔒 Security Considerations

- API Gateway as security perimeter
- Service-to-service communication within trusted network
- Input validation at API boundaries
- Database isolation per service

## 🚀 Deployment

### Docker Support (Future Enhancement)
```dockerfile
# Example Dockerfile for each service
FROM openjdk:21-jre-slim
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Kubernetes Deployment (Future Enhancement)
- Service discovery via Kubernetes DNS
- ConfigMaps for configuration
- Horizontal Pod Autoscaling
- Ingress for external access

## 📈 Scalability

- **Horizontal Scaling**: Multiple instances of each service
- **Load Balancing**: Client-side via Eureka
- **Database Scaling**: Can migrate to PostgreSQL with read replicas
- **Caching**: Redis can be added for frequently accessed data

## 🔄 CI/CD Pipeline

GitHub Actions workflow included for:
- Automated testing
- Code quality checks
- Docker image building
- Deployment automation

---

**Perfect for TCS/NRMS Background**: This project demonstrates enterprise-grade microservices architecture with patterns commonly used in airline and booking systems, making it ideal for showcasing distributed system expertise in interviews.