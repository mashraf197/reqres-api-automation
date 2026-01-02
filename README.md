# ReqRes API Automation – User Management Workflow

## Project Overview
This project demonstrates automated API testing for a user management workflow using the public ReqRes API (https://reqres.in).
It is designed as a simple, maintainable, and interview-ready example of API automation using Java and Rest Assured.

The automation validates a complete CRUD workflow:
- Create a user
- Update the user
- Retrieve the user
- Delete the user
- Verify the user is no longer available

The project also demonstrates response data extraction, request chaining, logging, and reporting using Allure.

---

## Scope of Automation

| Action | HTTP Method | Endpoint |
|------|------------|----------|
| Create User | POST | /api/users |
| Update User | PUT | /api/users/{id} |
| Get User | GET | /api/users/{id} |
| Delete User | DELETE | /api/users/{id} |
| Verify Deletion | GET | /api/users/{id} |

---

## Automation Approach

### 1. Architecture
The project follows a layered architecture:

- **Base API Layer**
  - Centralized configuration (base URL, headers, authentication, logging)
  - Shared RequestSpecification
  - Allure request/response integration

- **API Client Layer**
  - One method per endpoint
  - Explicit HTTP methods (POST, PUT, GET, DELETE)
  - Returns Rest Assured Response objects

- **Model Layer**
  - Plain Java objects for request bodies
  - Automatic JSON serialization using Jackson

- **Test Layer**
  - TestNG-based test cases
  - Clear separation of actions
  - Response chaining using extracted IDs
  - Soft assertions where mock behavior is expected

---

### 2. Test Design
The workflow is implemented as five independent test cases executed in sequence using TestNG priorities:

1. Create user and extract ID
2. Update user using extracted ID
3. Get user and verify update (tolerant of mock behavior)
4. Delete user
5. Get user after deletion and verify not found

Each test contains focused assertions for its specific responsibility.

---

### 3. Handling ReqRes Mock Behavior
ReqRes is a mock API:
- Created users may not persist
- GET requests may return 404 even after successful create/update

The tests are written to:
- Assert guaranteed behavior strictly
- Accept expected mock behavior where applicable
- Still demonstrate correct automation design

---

## Tools and Technologies

- **Java 17**
- **Rest Assured** – API testing
- **TestNG** – Test framework
- **Allure Report** – Test reporting
- **Maven** – Build and dependency management
- **Jackson** – JSON serialization

---

## Project Structure
```
src
 └── test
     └── java
         └── reqres
             ├── api
             │   └── ReqresClient.java
             ├── base
             │   └── BaseApi.java
             ├── models
             │   ├── CreateUserRequest.java
             │   └── UpdateUserRequest.java
             └── test
                 └── ReqresUserWorkflowTest.java
```

---

## How to Run

### Prerequisites
- Java 17
- Maven
- Internet access

### Run Tests
```bash
mvn clean test
```

### Generate Allure Report
```bash
mvn allure:report
```

To view the report:
```bash
mvn allure:serve
```

---

## Notes
- An API key is passed in request headers to bypass Cloudflare protection
- All requests and responses are logged
- The project is intentionally simple and extensible

---

## Conclusion
This project demonstrates clean API automation design, response chaining, and reporting integration.
It can be used as a technical task submission or as a foundation for larger API automation frameworks.
