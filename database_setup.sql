-- Database Setup Script for Project Management System
-- Run this script in PostgreSQL before starting the application

-- Create the database
CREATE DATABASE project_management_db;

-- Connect to the database
\c project_management_db;

-- The tables will be automatically created by Hibernate when the application starts
-- This is because spring.jpa.hibernate.ddl-auto=update is set in application.properties

-- Optional: Create a user with specific privileges (if needed)
-- CREATE USER project_user WITH PASSWORD 'your_password';
-- GRANT ALL PRIVILEGES ON DATABASE project_management_db TO project_user;
