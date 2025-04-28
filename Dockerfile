# First Stage: Build the JAR
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Second Stage: Run the JAR
FROM openjdk:17
WORKDIR /app

# Copy the built JAR from the first stage
COPY --from=build /app/target/vby-software.jar app.jar

# Expose the port
EXPOSE 8082

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]