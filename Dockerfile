#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

WORKDIR /app

# Add the application's jar to the image
COPY target/restapi-0.0.1-SNAPSHOT.jar restapi-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java", "-jar", "restapi-0.0.1-SNAPSHOT.jar"]