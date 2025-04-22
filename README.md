# Cleaning System

A Spring Boot application for managing a cleaning service platform.

## Features

- User account management
- User profile management
- Service listing management
- Role-based access control (User Admin, Cleaner, Home Owner, Platform Manager)

## Technologies Used

- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- MySQL Database
- Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven (optional, can use the Maven wrapper)

### Database Setup

1. Create a MySQL database named `db_cleaningsystem`
2. Update the database connection settings in `src/main/resources/application.properties` if needed

### Building the Application

Using Maven wrapper:
```
./mvnw clean install
```

Using Maven (if installed):
```
mvn clean install
```

### Running the Application

Using Maven wrapper:
```
./mvnw spring-boot:run
```

Using Maven (if installed):
```
mvn spring-boot:run
```

The application will be available at http://localhost:8080

## API Endpoints

### User Account Management

- `GET /api/users` - Get all user accounts
- `GET /api/users/{id}` - Get a specific user account
- `POST /api/users` - Create a new user account
- `PUT /api/users/{id}` - Update a user account
- `DELETE /api/users/{id}` - Delete a user account

### User Profile Management
- `GET /api/profiles`           - Get all profiles
- `GET /api/profiles/{id}`      - Get profile by ID
- `POST /api/profiles`          - Create new profile
- `PUT /api/profiles/{id}`      - Update profile
- `DELETE /api/profiles/{id}`   - Delete profile
- `GET /api/profiles/search`    - Search profiles by name

## Project Structure

- `src/main/java/com/cleaningsystem/controller` - REST controllers
- `src/main/java/com/cleaningsystem/model` - Domain models
- `src/main/java/com/cleaningsystem/dao` - Data access objects
- `src/main/java/com/cleaningsystem/db` - Database connection utilities
- `src/main/java/com/cleaningsystem/dto` - Data transfer objects 