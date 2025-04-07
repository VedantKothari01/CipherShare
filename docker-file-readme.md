This is for reference for executing Docker and not part of the project files:


Running on CLI:

# Create network
docker network create cipher-net

# Build and run Eureka Server
docker build -t eureka-server ./eureka-server
docker run -d -p 8761:8761 --network cipher-net --name eureka eureka-server

# Build and run API Gateway (JDK 21)
docker build -t api-gateway ./api-gateway
docker run -d -p 8080:8080 --network cipher-net -e JAVA_TOOL_OPTIONS="-XX:+UseZGC" api-gateway




#####KUBERNETES#####
# Create deployment with JDK 21 base image
kubectl create deployment user-service \
--image=eclipse-temurin:21-jre-jammy \
-- /app/app.jar \
--spring.profiles.active=prod

# Set resource limits for ZGC
kubectl set resources deployment user-service \
--limits=memory=512Mi \
--requests=memory=256Mi