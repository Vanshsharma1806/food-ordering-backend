# ----------------------------
# Stage 1: Build the jar
# ----------------------------
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the jar (skip tests)
RUN mvn clean package -DskipTests

# ----------------------------
# Stage 2: Run the app
# ----------------------------
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the jar built in Stage 1
COPY --from=build /app/target/*.jar app.jar

# Expose the port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
