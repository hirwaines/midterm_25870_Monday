# Entity Relationship Diagram (ERD) - Project Management System

## Overview
This Project Management System contains **6 entities** with well-defined relationships to manage users, their profiles, locations, projects, and tags.

---

## Entities and Their Attributes

### 1. **Location**
- **Primary Key**: `id` (UUID)
- **Attributes**:
  - `code` (String) - Unique location code
  - `name` (String) - Location name
  - `type` (Enum: ELocationType) - PROVINCE, DISTRICT, SECTOR, CELL, VILLAGE
  - `parent_id` (UUID, Foreign Key) - Self-referencing for hierarchical structure

### 2. **User**
- **Primary Key**: `id` (UUID)
- **Attributes**:
  - `email` (String) - User's email address
  - `password` (String) - Encrypted password
  - `role` (String) - User role (e.g., ADMIN, USER, MANAGER)
  - `location_id` (UUID, Foreign Key) - References Location

### 3. **UserProfile**
- **Primary Key**: `id` (UUID)
- **Attributes**:
  - `firstName` (String) - User's first name
  - `lastName` (String) - User's last name
  - `phoneNumber` (String) - Contact number
  - `user_id` (UUID, Foreign Key, Unique) - References User

### 4. **Project**
- **Primary Key**: `id` (UUID)
- **Attributes**:
  - `title` (String, Unique) - Project title
  - `description` (String) - Project description
  - `year` (Integer) - Year of project
  - `user_id` (UUID, Foreign Key) - References User (project owner)

### 5. **Tag**
- **Primary Key**: `id` (UUID)
- **Attributes**:
  - `name` (String, Unique) - Tag name (e.g., "Java", "Web Development", "AI")

### 6. **ProjectTags** (Join Table)
- **Composite Primary Key**: (`project_id`, `tag_id`)
- **Attributes**:
  - `project_id` (UUID, Foreign Key) - References Project
  - `tag_id` (UUID, Foreign Key) - References Tag

---

## Relationships Explanation

### 1. **Location ↔ Location (Self-Referencing - One-to-Many)**
- **Relationship Type**: Hierarchical Self-Join
- **Cardinality**: One-to-Many (1:N)
- **Logic**: 
  - A location can have multiple child locations (e.g., a PROVINCE contains multiple DISTRICTs)
  - Each location (except top-level) has one parent location
  - This creates a hierarchical structure: PROVINCE → DISTRICT → SECTOR → CELL → VILLAGE
- **Foreign Key**: `parent_id` in Location references `id` in Location
- **Example**: 
  - Kigali (PROVINCE) → Gasabo (DISTRICT) → Remera (SECTOR) → Rukiri (CELL) → Amahoro (VILLAGE)

### 2. **Location ↔ User (One-to-Many)**
- **Relationship Type**: One-to-Many
- **Cardinality**: 1:N
- **Logic**: 
  - One location can have multiple users residing in it
  - Each user belongs to exactly one location
  - This helps in geographical user management and filtering
- **Foreign Key**: `location_id` in User references `id` in Location
- **Example**: Multiple users can live in "Kigali" location

### 3. **User ↔ UserProfile (One-to-One)**
- **Relationship Type**: One-to-One
- **Cardinality**: 1:1
- **Logic**: 
  - Each user has exactly one profile containing personal details
  - Each profile belongs to exactly one user
  - Separates authentication data (User) from personal information (UserProfile)
  - Cascade ALL ensures profile is deleted when user is deleted
- **Foreign Key**: `user_id` in UserProfile references `id` in User (Unique constraint)
- **Example**: User "john@example.com" has one profile with name "John Doe"

### 4. **User ↔ Project (One-to-Many)**
- **Relationship Type**: One-to-Many
- **Cardinality**: 1:N
- **Logic**: 
  - One user can create/own multiple projects
  - Each project belongs to exactly one user (project owner)
  - Tracks project ownership and responsibility
- **Foreign Key**: `user_id` in Project references `id` in User
- **Example**: User "Alice" can own projects: "E-Commerce App", "Mobile Game", "AI Chatbot"

### 5. **Project ↔ Tag (Many-to-Many)**
- **Relationship Type**: Many-to-Many
- **Cardinality**: M:N
- **Logic**: 
  - One project can have multiple tags (e.g., "Java", "Spring Boot", "REST API")
  - One tag can be associated with multiple projects
  - Enables project categorization and filtering by technology/domain
  - Implemented using join table `project_tags`
- **Join Table**: `project_tags` with foreign keys:
  - `project_id` references `id` in Project
  - `tag_id` references `id` in Tag
- **Example**: 
  - Project "E-Commerce App" has tags: ["Java", "Spring Boot", "PostgreSQL", "REST API"]
  - Tag "Java" is used in projects: ["E-Commerce App", "Banking System", "Inventory Manager"]

---

## ERD Diagram (Text Representation)

```
┌─────────────────────────────────────────────────────────────────────────┐
│                         LOCATION (Hierarchical)                         │
│─────────────────────────────────────────────────────────────────────────│
│ PK: id (UUID)                                                           │
│     code (String)                                                       │
│     name (String)                                                       │
│     type (Enum: PROVINCE, DISTRICT, SECTOR, CELL, VILLAGE)             │
│ FK: parent_id (UUID) ──┐                                               │
└─────────────────────────┼───────────────────────────────────────────────┘
                          │ Self-Reference (1:N)
                          └──────────┐
                                     │
                          ┌──────────┘
                          │
                          │ (1:N)
                          ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                                  USER                                   │
│─────────────────────────────────────────────────────────────────────────│
│ PK: id (UUID)                                                           │
│     email (String)                                                      │
│     password (String)                                                   │
│     role (String)                                                       │
│ FK: location_id (UUID) ─────────────────────────────────────────────────┘
└─────────────────────────┬───────────────────────────────────────────────┘
                          │
                          │ (1:1)
                          ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                             USER_PROFILE                                │
│─────────────────────────────────────────────────────────────────────────│
│ PK: id (UUID)                                                           │
│     firstName (String)                                                  │
│     lastName (String)                                                   │
│     phoneNumber (String)                                                │
│ FK: user_id (UUID, UNIQUE)                                              │
└─────────────────────────────────────────────────────────────────────────┘

                          ┌─────────────────────────────────────────────────┐
                          │              USER (from above)                  │
                          └─────────────────────────────────────────────────┘
                                     │
                                     │ (1:N)
                                     ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                                PROJECT                                  │
│─────────────────────────────────────────────────────────────────────────│
│ PK: id (UUID)                                                           │
│     title (String, UNIQUE)                                              │
│     description (String)                                                │
│     year (Integer)                                                      │
│ FK: user_id (UUID)                                                      │
└─────────────────────────┬───────────────────────────────────────────────┘
                          │
                          │ (M:N)
                          ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                          PROJECT_TAGS (Join Table)                      │
│─────────────────────────────────────────────────────────────────────────│
│ PK: (project_id, tag_id)                                                │
│ FK: project_id (UUID) ──────────────────────────────────────────────────┘
│ FK: tag_id (UUID) ──────┐
└─────────────────────────┼───────────────────────────────────────────────┘
                          │
                          │ (M:N)
                          ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                                  TAG                                    │
│─────────────────────────────────────────────────────────────────────────│
│ PK: id (UUID)                                                           │
│     name (String, UNIQUE)                                               │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Database Constraints

1. **Primary Keys**: All tables use UUID as primary key for global uniqueness
2. **Unique Constraints**:
   - `User.email` - No duplicate emails
   - `Project.title` - No duplicate project titles
   - `Tag.name` - No duplicate tag names
   - `UserProfile.user_id` - One profile per user
3. **Foreign Key Constraints**:
   - Maintain referential integrity
   - Cascade operations where appropriate (User → UserProfile)
4. **Enum Constraint**: Location.type restricted to predefined values

---

## Business Logic Summary

This ERD supports a comprehensive project management system where:
- **Users** are registered with authentication credentials and geographical location
- **User profiles** store personal information separately from credentials
- **Locations** form a hierarchical structure representing administrative divisions
- **Projects** are owned by users and can be categorized
- **Tags** provide flexible categorization for projects (technologies, domains, etc.)
- The system enables queries like:
  - "Find all users in a specific province"
  - "Get all projects by a user"
  - "Find projects tagged with 'Java' and 'Spring Boot'"
  - "List all districts under Kigali province"

---

## Total Tables: 6
1. Location
2. User
3. UserProfile
4. Project
5. Tag
6. ProjectTags (Join Table)

**All relationships are properly normalized and follow database design best practices.**
