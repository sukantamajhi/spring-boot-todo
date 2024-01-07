# Use an official AdoptOpenJDK 17 runtime as a parent image
FROM eclipse-temurin:17-jdk-jammy

# Set the working directory in the container
WORKDIR /app

COPY . .

RUN ./gradlew build

# Copy the application JAR file into the container at /app
COPY build/libs/spring-boot-todo-0.0.1.jar /app/spring-todo.jar

# Specify the command to run on container start
CMD ["java", "-jar", "spring-todo.jar"]