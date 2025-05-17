# Spring Boot Leave Request Application

This project is a Spring Boot application that manages leave requests for employees. It provides a web interface for users to view and manage leave requests.

## Project Structure

The project is organized as follows:

```
spring-boot-app
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           ├── SpringBootAppApplication.java
│   │   │           ├── controller
│   │   │           │   └── AgendaLeaveRequestController.java
│   │   │           ├── model
│   │   │           │   ├── Employee.java
│   │   │           │   ├── LeaveRequest.java
│   │   │           │   └── User.java
│   │   │           └── repository
│   │   │               └── LeaveRequestRepository.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── templates
│   │           └── agenda.html
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Setup Instructions

1. **Clone the repository:**
   ```
   git clone <repository-url>
   cd spring-boot-app
   ```

2. **Build the project:**
   ```
   ./mvnw clean install
   ```

3. **Run the application:**
   ```
   ./mvnw spring-boot:run
   ```

4. **Access the application:**
   Open your web browser and navigate to `http://localhost:8080`.

## Usage

- The application allows users to view and manage leave requests for themselves and their direct staff.
- Users can submit new leave requests, view existing requests, and check the status of their requests.

## Dependencies

This project uses the following dependencies:

- Spring Boot
- Spring Data JPA
- Thymeleaf
- H2 Database (for development and testing)

## License

This project is licensed under the MIT License. See the LICENSE file for more details.