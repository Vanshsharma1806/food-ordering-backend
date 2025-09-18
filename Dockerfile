# Use official OpenJDK 17 slim image
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Copy pom.xml first (for caching dependencies)
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the jar inside container
RUN mvn clean package -DskipTests

# Expose port 8080
EXPOSE 8080

# Run the jar directly from target folder
ENTRYPOINT ["java", "-jar", "target/$(ls target | grep .jar | head -n 1)"]
