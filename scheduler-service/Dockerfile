FROM gradle:8.11.1-jdk17
COPY build/libs/scheduler-service-1.0.jar scheduler-service.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "scheduler-service.jar"]
