# Dockerfile

FROM openjdk:23-slim
WORKDIR /app

# Copy only the final jar
COPY target/*.jar app.jar

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
