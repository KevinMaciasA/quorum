# Quorum-RestAPI

This is project holds all the logic used in quorum.com a Question based Forum. With this API
users can register, login, and manage posts. The application is made with Java Spring boot and uses Auth0 for JWT utilities and adheres to a stateless session management approach.

## Table of Contents

- [Requirements](#requirements)
- [Installation](#installation)
- [Configuration](#configuration)
- [Endpoints](#endpoints)
  - [User Endpoints](#user-endpoints)
  - [Post Endpoints](#post-endpoints)
- [Running the Application](#running-the-application)
- [License](#license)

## Requirements

- Java 17 or higher
- Maven
- MySQL

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/KevinMaciasA/quorum-api
   cd quorum-api
   ```

## Configuration

Add your database configuration and JWT secret key in the src/main/resources/application.yaml file:

```YAML
spring:
  profiles:
    active: dev
  application:
    name: quorum-api
  datasource:
    url: jdbc:mysql://localhost/quorum_api?createDatabaseIfNotExist=true&serverTimezone=UTC
    username: {{your_username}}
    password: {{your_password}}
  jpa:
    hibernate:
      ddl-auto: update
jwt:
  secret: {{your_secret}}
  issuer: Quorum
  expirationInHours: 12
  timeOffset: -3
```

## Endpoints

### User Endpoints

Register

- URL: `/register`
- Method: `POST`
- Request Body:

```json
{
  "username": "user1",
  "email": "user1@example.com",
  "password": "password"
}
```

Login

- URL: `/login`
- Method: `POST`
- Request Body:

```json
{
  "email": "user1@example.com",
  "password": "password"
}
```

- Response:

```json
{
  "token": "jwt_token"
}
```

### Post Endpoints

Get All Posts

- URL: `/posts`
- Method: `GET`
- Headers:
  - Authorization: `Bearer <jwt_token>`
- Response:

```json
TODO: add pagination response
```

Create Post

- URL: `/posts`
- Method: `POST`
- Headers:
  - Authorization: `Bearer <jwt_token>`
- Request Body:

```json
{
  "title": "New Post",
  "content": "Content of the new post"
}
```

- Response:

```json
{
  "id": 1,
  "title": "New Post",
  "content": "Content of the new post",
  "authorName": "user1",
  "authorEmail": "user1@example.com",
  "status": "Active",
  "createdAt": "20XX-XX-XXT00:00"
}
```

Update Post

- URL: `/posts/{id}`
- Method: `PUT`
- Headers:
  - Authorization: `Bearer <jwt_token>`
- Request Body:

```json
{
  "title": "Updated Post Title",
  "content": "Updated content of the post",
  "status": "Updated Post status"
}
```

- Response:

```json
{
  "id": 1,
  "title": "Updated Post Title",
  "content": "Updated content of the post",
  "authorName": "user1",
  "authorEmail": "user1@example.com",
  "status": "Updated Post status",
  "createdAt": "20XX-XX-XXT00:00"
}
```

Delete Post

- URL: `/posts/{id}`
- Method: `DELETE`
- Headers:
  - Authorization: `Bearer <jwt_token>`

## Running the Application

1. Build the application:

```bash
mvn clean install
```

2. Run the application:

```bash
mvn spring-boot:run
```

3. Access the application at `http://localhost:8080`.
