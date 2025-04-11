# Dockerfile

FROM openjdk:21-slim
WORKDIR /app


COPY target/*.jar app.jar

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
