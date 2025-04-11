# Dockerfile

FROM openjdk:21-slim
WORKDIR /app

# Install tools for startup checks and wget for downloading dependencies
RUN apt-get update && apt-get install -y netcat-openbsd wget && rm -rf /var/lib/apt/lists/*

# Create lib directory for dependencies
RUN mkdir -p /app/lib

# Download MySQL JDBC driver
RUN wget https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.28/mysql-connector-java-8.0.28.jar -O /app/lib/mysql-connector-java.jar

# Copy only the final jar
COPY target/*.jar app.jar

# Create startup script
RUN echo '#!/bin/sh \n\
echo "Waiting for MySQL to start..." \n\
while ! nc -z mysql-db 3306; do \n\
  sleep 2 \n\
done \n\
echo "MySQL started" \n\
echo "Waiting for Eureka Server to start..." \n\
while ! nc -z eureka-server 8761; do \n\
  sleep 2 \n\
done \n\
echo "Eureka Server started" \n\
# Additional wait to ensure Eureka is fully initialized \n\
sleep 25 \n\
java -Dspring.profiles.active=docker -Dlogging.level.root=DEBUG -Dlogging.level.org.hibernate=DEBUG -cp /app/lib/mysql-connector-java.jar:/app/app.jar org.springframework.boot.loader.JarLauncher \n\
' > /app/startup.sh && chmod +x /app/startup.sh

# Run the startup script
ENTRYPOINT ["/app/startup.sh"]
