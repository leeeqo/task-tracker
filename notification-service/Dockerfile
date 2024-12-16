FROM gradle:8.11.1-jdk17
COPY build/libs/notification-service-1.0.jar notification-service.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "notification-service.jar"]
