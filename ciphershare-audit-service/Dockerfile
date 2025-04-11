# Dockerfile

FROM openjdk:21-slim
WORKDIR /app

# Copy the JAR file
COPY target/*.jar app.jar

# Set the entrypoint to run the JAR directly
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
