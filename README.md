# Thinkon assessment

This project is a REST API for managing users, providing functionalities to create, retrieve, update, and delete user information.

## Getting Started

### Prerequisites

To run this project, you need to have the following installed:

- Java 17 or later
- Gradle
- A MySQL database

### Setup

1. Configure the database properties:

   Open the `src/main/resources/application.properties` file and update the database settings to match your environment:

    ```properties
    # Database Configuration
    spring.datasource.url=jdbc:mysql://<DB_HOST>:<DB_PORT>/<DB_NAME>
    spring.datasource.username=<DB_USERNAME>
    spring.datasource.password=<DB_PASSWORD>
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    ```

2. Server Port Configuration:

   The server will run on port `18089`. This is configured in `application.properties`:

    ```properties
    # Server Port
    server.port=18089
    ```
3. Execute the changes.sql file located in the resources folder.

## API Endpoints

The following endpoints are available:

- **Create a User**:  
  `POST /users`  
  Creates a new user with the provided details.

- **Retrieve a User**:  
  `GET /users/{id}`  
  Retrieves the details of a user with the given ID.

- **Update a User**:  
  `PUT /users/{id}`  
  Updates the details of a user with the given ID.

- **Delete a User**:  
  `DELETE /users/{id}`  
  Deletes the user with the given ID.

### Example of a Valid JSON for Creating or Updating a User:

```json
{
  "firstName" : "Rahul",
  "lastName" : "kukadiya",
  "username" : "rkukadiya",
  "email" : "rkukadiy@lakeheadu.ca",
  "phoneNumber" : "8073572050"
}
```
## Assumptions
* I have implemented basic validation for all user-related fields, including first name, last name, username, email, and phone number.
* I implemented a soft delete operation to remove the user from the database by setting the delete flag to 1 upon deletion.