# Dockerfile

FROM openjdk:21-slim
WORKDIR /app

# Copy the built jar
COPY target/*.jar app.jar

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
