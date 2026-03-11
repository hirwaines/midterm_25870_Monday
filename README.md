# Project Management System - Setup Instructions

## Prerequisites
- Java 21
- PostgreSQL 17.5 (or compatible version)
- Maven 3.x
- IDE (VS Code, IntelliJ IDEA, or Eclipse)

## Database Setup

### Step 1: Create the Database

Open PostgreSQL command line (psql) or pgAdmin and run:

```sql
CREATE DATABASE project_management_db;
```

### Step 2: Verify Database Connection

Make sure PostgreSQL is running on:
- Host: `localhost`
- Port: `5432`
- Username: `postgres`
- Password: `ines` (or update in `application.properties`)

### Step 3: Update Database Credentials (if needed)

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/project_management_db
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD_HERE
```

## Build and Run

### Option 1: Using Maven

```bash
# Clean and compile
mvn clean compile

# Run the application
mvn spring-boot:run
```

### Option 2: Using IDE

1. Open the project in your IDE
2. Wait for Maven dependencies to download
3. Run `ProjectManagementSystemApplication.java`

### Option 3: Build JAR and Run

```bash
# Build JAR file
mvn clean package -DskipTests

# Run the JAR
java -jar target/ProjectManagementSystem-0.0.1-SNAPSHOT.jar
```

## Verify Application is Running

The application should start on: **http://localhost:8081**

You should see:
```
Tomcat started on port 8081 (http)
Started ProjectManagementSystemApplication
```

## Database Tables

The following tables will be automatically created by Hibernate:

1. **locations** - Hierarchical location data
2. **users** - User authentication data
3. **user_profiles** - User personal information
4. **projects** - Project details
5. **tags** - Project tags/categories
6. **project_tags** - Join table for projects and tags

## API Endpoints

### Location Endpoints
- `POST /api/locations/save` - Save a new location
- `GET /api/locations/find?code={code}&name={name}` - Find location

### User Endpoints
- `POST /api/users/save` - Register a new user
- `GET /api/users/all?page=0&size=10&sortBy=email` - Get all users (paginated)
- `GET /api/users/province?code={code}&name={name}` - Get users by province

### Project Endpoints
- `POST /api/projects/save` - Create a new project
- `GET /api/projects/all?page=0&size=10&sortBy=title` - Get all projects (paginated)
- `GET /api/projects/check?title={title}` - Check if project title exists

### Tag Endpoints
- `POST /api/tags/save` - Create a new tag
- `GET /api/tags/all?page=0&size=10&sortBy=name` - Get all tags (paginated)
- `GET /api/tags/check?name={name}` - Check if tag exists

## Testing with Postman/cURL

### Example: Create a Location

```bash
curl -X POST http://localhost:8081/api/locations/save \
  -H "Content-Type: application/json" \
  -d '{
    "code": "KGL",
    "name": "Kigali",
    "type": "PROVINCE"
  }'
```

### Example: Create a User

```bash
curl -X POST http://localhost:8081/api/users/save \
  -H "Content-Type: application/json" \
  -d '{
    "user": {
      "email": "john@example.com",
      "password": "password123",
      "role": "USER"
    },
    "profile": {
      "firstName": "John",
      "lastName": "Doe",
      "phoneNumber": "+250788123456"
    },
    "locationId": "LOCATION_UUID_HERE"
  }'
```

### Example: Create a Project

```bash
curl -X POST http://localhost:8081/api/projects/save \
  -H "Content-Type: application/json" \
  -d '{
    "project": {
      "title": "E-Commerce Platform",
      "description": "Online shopping system",
      "year": 2024
    },
    "userId": "USER_UUID_HERE",
    "tags": ["Java", "Spring Boot", "PostgreSQL"]
  }'
```

## Troubleshooting

### Issue: Database connection failed
**Solution**: 
- Verify PostgreSQL is running
- Check database name, username, and password in `application.properties`
- Ensure database `project_management_db` exists

### Issue: Port 8081 already in use
**Solution**: 
- Change port in `application.properties`: `server.port=8082`
- Or stop the application using port 8081

### Issue: Repositories not found
**Solution**: 
- Ensure `@EnableJpaRepositories` is present in main application class
- Verify package names match directory structure

### Issue: Tables not created
**Solution**: 
- Check `spring.jpa.hibernate.ddl-auto=update` in `application.properties`
- Verify database connection is successful
- Check entity classes have proper JPA annotations

## Project Structure

```
ProjectManagementSystem/
├── src/
│   ├── main/
│   │   ├── java/com/ProjectManagementSystem/
│   │   │   ├── controller/      # REST Controllers
│   │   │   ├── modal/           # Entity Classes
│   │   │   ├── repository/      # JPA Repositories
│   │   │   ├── service/         # Business Logic
│   │   │   └── ProjectManagementSystem/
│   │   │       └── ProjectManagementSystemApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── ERD_Documentation.md         # ERD Explanation
├── ERD_Diagram.puml            # PlantUML ERD
├── ERD_Simple.puml             # Simple PlantUML ERD
├── database_setup.sql          # Database setup script
├── pom.xml
└── README.md
```

## Technologies Used

- **Spring Boot 4.0.3** - Application framework
- **Spring Data JPA** - Data persistence
- **Hibernate 7.2.4** - ORM
- **PostgreSQL 42.7.10** - Database driver
- **Maven** - Build tool
- **Java 21** - Programming language

## Contributors

- Your Name
- Course: [Your Course Name]
- Assignment: RESTful API with Spring Boot

## License

This project is for educational purposes.
