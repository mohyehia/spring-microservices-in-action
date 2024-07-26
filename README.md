# spring-microservices-in-action

# Table of Contents

- [Project Overview](#project-overview)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Running Tests](#running-tests)
- [Contributing](#contributing)
- [License](#license)


## Project Overview
This project is an implementation of a microservices architecture using Spring Boot. It consists of several services:

- `api-gateway`: An API gateway that routes requests to the appropriate service.
- `licence-service`: A service for managing licence information.
- `organization-service`: A service for managing organization information.

## Technologies
* Spring Boot 3
* Java 17
* Spring Cloud Gateway
* Spring Webflux
* Reactive Java
* PostgresQL
* MongoDB
* Flyway DB Migration
* Microservices Architecture
* Elasticsearch
* Filebeats
* Kibana
* Prometheus & Grafana
* Grafana Loki
* Grafana Tempo
* Maven
* Docker
* Docker-compose
* Kubernetes
* IntelliJ

## Prerequisites
- Docker
- Docker Compose
- Java 17
- Maven 3.9.8

## Getting Started
1. Clone the repository:
   ```
   git clone https://github.com/your-username/spring-microservices-in-action.git
   ```

2. Build the project:
   ```
   cd spring-microservices-in-action
   mvn clean install
   ```

3. Start the services using Docker Compose:
   ```
   docker-compose up
   ```

4. Access the services:
   - Organization Service: http://localhost:8082/organization-ms/api/v1/organizations
   - Licence Service: http://localhost:8082/license-ms/api/v1/organizations/{organizationId}/licences

## Running Tests
To run the unit tests, use the following command:
```
mvn test
```

## Contributing
Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request.

## License
This project is licensed under the [MIT License](LICENSE).