# 🚕 Cab Booking System Backend

[![Hacktoberfest](https://img.shields.io/badge/Hacktoberfest-2024-orange)](https://hacktoberfest.digitalocean.com/)
[![Java](https://img.shields.io/badge/Java-17-blue)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-green)](https://spring.io/projects/spring-boot)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](http://makeapullrequest.com)

A comprehensive Spring Boot backend system for a cab booking application with RESTful APIs. This project is **Hacktoberfest 2025 friendly** and welcomes contributions from developers of all skill levels!

## 🎯 Hacktoberfest 2025

This repository is participating in **Hacktoberfest 2025**! We welcome contributions from developers worldwide. Whether you're a beginner or an experienced developer, there are opportunities for everyone.

### 🤝 How to Contribute
1. Check our [Issues](../../issues) for `hacktoberfest` labeled tasks
2. Look for issues tagged with `good first issue` if you're a beginner
3. Comment on an issue to get it assigned to you
4. Fork the repository and create your feature branch
5. Make your changes and test them thoroughly
6. Submit a Pull Request with a clear description

### 🏷️ Contribution Areas
- 🐛 **Bug Fixes**: Help us identify and fix bugs
- ✨ **New Features**: Add exciting new functionality
- 📚 **Documentation**: Improve documentation and add examples
- 🧪 **Testing**: Write unit and integration tests
- 🎨 **UI/UX**: Enhance API design and user experience
- 🚀 **Performance**: Optimize code and database queries

## Features

- **User Management**: Registration, login, and profile management for customers, drivers, and admins
- **Driver Management**: Driver registration, verification, location tracking, and availability management
- **Cab Management**: Vehicle registration, status tracking, and cab assignment
- **Booking System**: Ride booking, driver assignment, real-time tracking, and payment processing
- **Location Services**: Real-time location tracking and history for drivers and rides
- **Authentication & Authorization**: Secure API endpoints with role-based access control
- **Data Validation**: Comprehensive input validation and error handling

## Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Database**: H2 (for development), MySQL (for production)
- **Security**: Spring Security with JWT
- **ORM**: Spring Data JPA with Hibernate
- **Build Tool**: Maven
- **Testing**: JUnit 5, Spring Boot Test

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/cabbooking/
│   │       ├── CabBookingSystemApplication.java
│   │       ├── config/          # Configuration classes
│   │       ├── controller/      # REST Controllers
│   │       ├── dto/            # Data Transfer Objects
│   │       ├── entity/         # JPA Entities
│   │       ├── exception/      # Exception handling
│   │       ├── repository/     # Data repositories
│   │       └── service/        # Business logic services
│   └── resources/
│       ├── application.properties
│       └── application-test.properties
└── test/
    └── java/
        └── com/cabbooking/     # Test classes
```

## API Endpoints

### User Management
- `POST /api/users/register` - Register new user
- `POST /api/users/login` - User login
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Driver Management
- `POST /api/drivers` - Register new driver
- `GET /api/drivers/available` - Get available drivers
- `GET /api/drivers/nearby` - Find nearby drivers
- `PUT /api/drivers/{id}/status` - Update driver status
- `PUT /api/drivers/{id}/location` - Update driver location

### Cab Management
- `POST /api/cabs` - Register new cab
- `GET /api/cabs/available` - Get available cabs
- `GET /api/cabs/type/{cabType}` - Get cabs by type
- `PUT /api/cabs/{id}/status` - Update cab status

### Booking Management
- `POST /api/bookings` - Create new booking
- `GET /api/bookings/{id}` - Get booking details
- `PUT /api/bookings/{bookingId}/assign-driver/{driverId}` - Assign driver
- `PUT /api/bookings/{bookingId}/status` - Update booking status
- `GET /api/bookings/pending` - Get pending bookings

### Location Services
- `POST /api/locations` - Log location data
- `GET /api/locations/driver/{driverId}` - Get driver location history
- `GET /api/locations/booking/{bookingId}` - Get booking location history

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Running the Application

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd cab-booking-system
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - API Base URL: `http://localhost:8080/cab-booking-api`
   - H2 Console: `http://localhost:8080/cab-booking-api/h2-console`
   - JDBC URL: `jdbc:h2:mem:testdb`
   - Username: `sa`
   - Password: (leave empty)

### Running Tests
```bash
mvn test
```

## Database Schema

### Core Entities
- **User**: Customer, driver, and admin user accounts
- **Driver**: Driver-specific information and verification status
- **Cab**: Vehicle information and current status
- **Booking**: Ride booking details and tracking
- **LocationLog**: Real-time location tracking data

### Entity Relationships
- One User can be one Driver (one-to-one)
- One Driver has one Cab (one-to-one)
- One User can have multiple Bookings (one-to-many)
- One Driver can have multiple Bookings (one-to-many)
- One Driver can have multiple LocationLogs (one-to-many)

## Configuration

### Database Configuration
- **Development**: H2 in-memory database
- **Production**: MySQL database (configuration in application.properties)

### Security Configuration
- JWT-based authentication
- Role-based authorization (CUSTOMER, DRIVER, ADMIN)
- CORS enabled for frontend integration

### Profiles
- **Default**: Development profile with H2 database
- **Test**: Test profile for running unit tests
- **Production**: Production profile (configure database URL and credentials)

## API Usage Examples

### Register a new customer
```bash
curl -X POST http://localhost:8080/cab-booking-api/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "1234567890",
    "password": "password123",
    "role": "CUSTOMER"
  }'
```

### Create a booking
```bash
curl -X POST http://localhost:8080/cab-booking-api/api/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "user": {"id": 1},
    "pickupAddress": "123 Main St",
    "pickupLatitude": 40.7128,
    "pickupLongitude": -74.0060,
    "dropoffAddress": "456 Oak Ave",
    "dropoffLatitude": 40.7589,
    "dropoffLongitude": -73.9851,
    "requestedCabType": "SEDAN",
    "requestedTime": "2024-01-15T10:30:00"
  }'
```

### Find nearby drivers
```bash
curl "http://localhost:8080/cab-booking-api/api/drivers/nearby?latitude=40.7128&longitude=-74.0060&radiusKm=5"
```

## Contributing

We love contributions! This project follows the [Contributor Covenant Code of Conduct](https://www.contributor-covenant.org/version/2/1/code_of_conduct/).

### 🚀 Quick Start for Contributors

1. **Fork the repository**
   ```bash
   git clone https://github.com/your-username/cab-booking-system.git
   cd cab-booking-system
   ```

2. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Set up the development environment**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Make your changes and test**
   ```bash
   mvn test
   ```

5. **Commit your changes**
   ```bash
   git commit -am 'Add: your feature description'
   ```

6. **Push to your fork**
   ```bash
   git push origin feature/your-feature-name
   ```

7. **Create a Pull Request**
   - Go to the original repository
   - Click "New Pull Request"
   - Provide a clear description of your changes

### 📋 Contribution Guidelines

- **Code Style**: Follow Java naming conventions and Spring Boot best practices
- **Testing**: Add unit tests for new features and bug fixes
- **Documentation**: Update README and add JavaDoc comments
- **Commit Messages**: Use clear, descriptive commit messages
- **Pull Requests**: Include a detailed description and reference any related issues

### 🏆 Recognition

Contributors will be:
- Added to our contributors list
- Mentioned in release notes
- Eligible for special recognition during Hacktoberfest

## 📞 Support & Community

- 💬 **Discussions**: Use [GitHub Discussions](../../discussions) for questions and ideas
- 🐛 **Bug Reports**: Create an [Issue](../../issues) with the bug template
- 💡 **Feature Requests**: Use the feature request template in Issues
- 📧 **Contact**: Reach out to maintainers for any questions

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Thanks to all contributors who help make this project better
- Inspired by real-world cab booking systems and best practices

## ⭐ Show Your Support

If you find this project helpful, please consider:
- ⭐ Starring the repository
- 🍴 Forking it for your own projects
- 📢 Sharing it with others
- 🤝 Contributing to make it even better!


MYSQL_ROOT_PASSWORD=root
MYSQL_DATABASE=my_app_db
MYSQL_USER=user
MYSQL_PASSWORD=password