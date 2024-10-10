## College Directory Application

### Framework and Language
- **Java** with the **Spring Boot** framework.

### API Development
- Developed **RESTful API endpoints** for:
  - User authentication and authorization.
  - CRUD operations for user profiles and records.
  - Fetching student and faculty details.

### Authentication and Security
- Implemented user authentication and authorization using **JWT** or session-based methods.
- Protected API endpoints to ensure that only authorized users can access specific functionalities, utilizing **role-based access control** for enhanced security.

### Database Management
- Utilized **PostgreSQL** for data storage.
- Designed key entities, including:
  - **User**: 
    - Stores login credentials and contact information.
    - Differentiates roles (Student, Faculty Member, Administrator).
  - **StudentProfile**, **FacultyProfile**, and **AdministratorProfile**: 
    - Extend the User entity to include additional details specific to each role.
  - **Course**: 
    - Contains course information linked to the FacultyProfile.
  - **Enrollment**: 
    - Represents the many-to-many relationship between students and courses.
  - **Department**: 
    - Stores information about various departments, facilitating associations with users and courses.

### Data Handling
- Implemented data validation and error handling across API endpoints.
- Ensured that CRUD operations for student and faculty records reflect changes immediately in the database.

---
