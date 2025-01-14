FROM gradle:8.11.1-jdk17
COPY build/libs/security-service-1.0.jar security-service.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "security-service.jar"]
