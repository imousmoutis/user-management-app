# user-management-app
This application provides CRUD user management operations.

## Technology Stack
The application is built using:
- Java 14
- Spring Boot 2.5.4
- MariaDB 10.5.12
- Angular 12.1.4
- Gradle 6.8.1
- Node 14.17.6
- npm 6.14.15
- Nginx 1.21.3

## Deploy
The application can be deployed using Docker with the following steps:

1) Build the images.
   ```
   gradlew.bat clean build docker
   ```

2) Start the containers.
   ```
   docker-compose up
   ```

3) The application is running at: http://localhost:8080
   
4) The logs of the running containers can be found here: http://localhost:9999

