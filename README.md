Account Backend
=======
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://github.com/persapiens-classes/account-backend/actions/workflows/maven.yml/badge.svg)](https://github.com/persapiens-classes/account-backend/actions)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=persapiens-classes_account-backend&metric=code_smells)](https://sonarcloud.io/project/issues?issueStatuses=OPEN%2CCONFIRMED&id=persapiens-classes_account-backend)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=persapiens-classes_account-backend&metric=coverage)](https://sonarcloud.io/component_measures?id=persapiens-classes_account-backend&metric=coverage&view=list)

## Overview

This is the pedagogical example app of the discipline [Qualidade de Software (Page 109)](https://github.com/persapiens-classes/ifrn-software-quality) - [Tecnologia em Análise e Desenvolvimento de Sistemas - TADS](https://sites.google.com/escolar.ifrn.edu.br/diatinf/cursos/superiores/an%C3%A1lise-e-desenvolvimento-de-sistemas?authuser=0) - [Diretoria Acadêmica de Gestão e Tecnologia da Informação - DIATINF](https://diatinf.ifrn.edu.br) - [Campus Natal Central - CNAT](https://portal.ifrn.edu.br/campus/natalcentral) - [Instituto Federal do Rio Grande do Norte - IFRN](https://portal.ifrn.edu.br/).

## Features

- Secure RESTful API for account management
- JWT-based authentication and authorization
- CRUD operations for accounts, categories, owners, and entries
- Database migrations and schema management
- Automated tests and code quality checks
- Containerized deployment with Docker and Caddy

## Technologies

This backend API is built with **Spring Boot** and leverages the following technologies:

- **Java 21** (Eclipse Temurin)
- **Spring Boot** (REST API, Dependency Injection, Validation)
- **Spring Security** (JWT authentication, role-based access)
- **Spring Data JPA** (Persistence layer)
- **Hibernate** (ORM)
- **PostgreSQL** (Database)
- **Flyway** (Database migrations)
- **OpenAPI/Swagger** (API documentation)
- **JUnit 5** (Unit and integration testing)
- **AssertJ** (Fluent assertions)
- **Mockito** (Mocking in tests)
- **SonarCloud** (Code quality and coverage)
- **Docker** (Containerization)
- **Caddy** (Reverse proxy)
- **Maven** (Build automation)
- **Lombok** (Boilerplate code reduction)
- **GitHub Actions** (CI/CD)

## Getting Started

1. **Clone the repository**
2. **Build the project**
   ```sh
   ./mvnw clean package
   ```
3. **Run with Docker**
   ```sh
   docker compose up
   ```
4. **Access API documentation**
   - Visit `http://localhost:8080/swagger-ui/` for OpenAPI docs

## License

This project is licensed under the [Apache License 2.0](LICENSE).