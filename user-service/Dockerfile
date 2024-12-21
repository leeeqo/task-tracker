FROM gradle:8.11.1-jdk17
COPY build/libs/user-service-1.0.jar user-service.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "user-service.jar"]
