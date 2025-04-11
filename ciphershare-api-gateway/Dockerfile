# Dockerfile

FROM openjdk:21-slim
WORKDIR /app

# Install tools for startup checks
RUN apt-get update && apt-get install -y netcat-openbsd && rm -rf /var/lib/apt/lists/*

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
echo "Waiting for User Service to start..." \n\
echo "Skipping user service check for now to proceed with startup" \n\
sleep 30 \n\
java -Dspring.profiles.active=docker -Dlogging.level.root=DEBUG -Dlogging.level.org.springframework.cloud.gateway=DEBUG -jar app.jar \n\
' > /app/startup.sh && chmod +x /app/startup.sh

# Run the startup script
ENTRYPOINT ["/app/startup.sh"]
