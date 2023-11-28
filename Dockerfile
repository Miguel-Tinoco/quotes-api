# Use the official Gradle image as the build image
FROM gradle:7.3.3-jdk17 AS build

WORKDIR /app

# Copy Gradle build files for caching dependencies
COPY gradlew /app
COPY gradle /app/gradle

COPY build.gradle.kts /app
COPY settings.gradle.kts /app

# Copy the source code
COPY src /app/src

# Build the application
RUN ./gradlew build -x test

# Use a lightweight Java runtime as the final image
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the JAR file from the build image to the final image
COPY --from=build /app/build/libs/quotes-0.0.1-SNAPSHOT.jar ./app.jar
