# Project Name

Quotes API

## Prerequisites

Before you begin, ensure you have met the following requirements:
- You have installed Docker on your system. [Docker Installation Guide](https://docs.docker.com/get-docker/)

## Running the Application Locally
To run the application locally, follow these steps:

#### 1. Clone the Repository
#### 2. Start Docker
#### 3. Run Commands:

##### 3.1. Start the Application on Port 8080
```bash
docker-compose build app && docker-compose run -p 8080:8080 app
```
##### 3.2. Run Tests
This will run the unit and integrations test
```bash
docker-compose build app && docker-compose run test
```
##### 3.3. Run Lint (Ktlint Check)
```bash
docker-compose build app && docker-compose run lint
```
