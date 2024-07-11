# Learning-Management-System
This project is a Learning Management System (LMS) developed using Java Spring Boot. The system allows administrators to manage courses and users, and users to view and enroll in courses.

#Features
Admin Dashboard:

Add, update, and delete courses.
Manage user accounts.
User Interface:

View available courses.
Enroll in courses.
View enrolled courses.
Security:

Role-based authentication (admin and user roles).
Password encryption using AES.
Security questions for user validation.


#Technologies Used
Backend:

Java Spring Boot
Hibernate ORM
MySQL database
Frontend:

Thymeleaf templates
HTML/CSS/JavaScript

#Setup Instructions
Clone the repository:

bash
Copy code
git clone https://github.com/your-username/lms-project.git
cd lms-project
Database Configuration:

Create a MySQL database named lms_db.
Configure your database connection in application.properties.
Build and Run:

Build the project using Maven:
bash
Copy code
mvn clean package
Run the application:
bash
Copy code
java -jar target/lms-project-0.0.1-SNAPSHOT.jar
Access the Application:

Open your web browser and go to http://localhost:8080.


#Usage
Admin Actions:

Log in as admin with username admin and password admin123.
Manage courses and users from the admin dashboard.
User Actions:

Register for a new account or log in with existing credentials.
Browse available courses and enroll in desired courses.

![Screenshot 2024-07-11 225624](https://github.com/s-harsh/Learning-Management-System/assets/77559862/0f98ad03-3d04-42d3-99ad-74f6ab6cc050)
![Screenshot 2024-07-11 225612](https://github.com/s-harsh/Learning-Management-System/assets/77559862/4c9e1f02-f929-4062-be9d-d740ddb9a6e2)
![Screenshot 2024-07-11 225600](https://github.com/s-harsh/Learning-Management-System/assets/77559862/72f26965-501a-4e3e-8f2a-37676bfbd764)
![Screenshot 2024-07-11 225238](https://github.com/s-harsh/Learning-Management-System/assets/77559862/b3ee46be-7b62-4949-886b-f525771594ef)
![Screenshot 2024-07-11 223517](https://github.com/s-harsh/Learning-Management-System/assets/77559862/8cfb7a97-eb8d-4474-9754-499c4665b6a7)
![Screenshot 2024-07-11 223501](https://github.com/s-harsh/Learning-Management-System/assets/77559862/2c3f8717-237a-4357-bc7a-2815941fb06a)
![Screenshot 2024-07-11 225729](https://github.com/s-harsh/Learning-Management-System/assets/77559862/e25aca4e-2796-4fea-bbc0-b704613d2ae6)
![Screenshot 2024-07-11 225722](https://github.com/s-harsh/Learning-Management-System/assets/77559862/5526cb29-a210-45d1-913b-5ed2ad990e5e)
![Screenshot 2024-07-11 225709](https://github.com/s-harsh/Learning-Management-System/assets/77559862/d28ec577-4690-4a48-9d58-d57f0a63b270)
![Screenshot 2024-07-11 225658](https://github.com/s-harsh/Learning-Management-System/assets/77559862/48f9f401-11a3-4a1a-b7d2-37ab6f42255c)
