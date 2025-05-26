# Backend Dockerfile for Spring Boot Application
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make the Maven wrapper executable
RUN chmod +x ./mvnw

# Download Maven dependencies (leverage Docker cache)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build the application
RUN ./mvnw package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy JAR from the build stage
COPY --from=build /app/target/demo-*.jar app.jar

# Expose the application port
EXPOSE 8080

# Set entry point
ENTRYPOINT ["java", "-jar", "app.jar"]