# ---------- BUILD STAGE ----------
# Java 21 for compiling the project
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy Maven wrapper and config
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (better Docker cache)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build the Spring Boot JAR
RUN ./mvnw clean package -DskipTests


# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

# Install netcat
RUN apt-get update && apt-get install -y netcat-openbsd && rm -rf /var/lib/apt/lists/*


# Copy the generated JAR from build stage
COPY --from=build /app/target/blackjack-0.0.1-SNAPSHOT.jar app.jar

# Copy wait-for-it script
COPY wait-for-it.sh .

# Make sure it’s executable
RUN chmod +x wait-for-it.sh

# Spring Boot default port
EXPOSE 8080

# Run the API only after MySQL is ready
ENTRYPOINT ["./wait-for-it.sh", "mysql", "3306", "java", "-jar", "app.jar"]

