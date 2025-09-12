# Step 1: Build the application
FROM maven:3.9.8-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy Maven configuration files and download dependencies first
COPY pom.xml .
RUN mvn dependency:go-offline

# Then copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Step 2: Final optimized image
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/spring-security-asymmetric-encryption-0.0.1-SNAPSHOT.jar /app/
#COPY .env .env
#COPY src/main/resources/keys /app/keys

# Port for the HTTP-Server
EXPOSE 8080

# Non-sensitive environment variables
ENV DB_HOST=localhost
ENV DB_USERNAME=username
ENV DB_PASSWORD=password
ENV DB_PORT=5432
ENV DB_NAME=spring_security
ENV SERVER_PORT=8080

# Run the application
#ENTRYPOINT ["java", "-jar", "/app/spring-security-asymmetric-encryption-0.0.1-SNAPSHOT.jar"]

# Run the application with optimized JVM settings
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/spring-security-asymmetric-encryption-0.0.1-SNAPSHOT.jar"]