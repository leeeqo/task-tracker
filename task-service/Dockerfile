FROM gradle:8.11.1-jdk17
COPY build/libs/task-service-1.0.jar task-service.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "task-service.jar"]
