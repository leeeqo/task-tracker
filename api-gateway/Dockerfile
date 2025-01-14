FROM gradle:8.11.1-jdk17
COPY build/libs/api-gateway-1.0.jar api-gateway.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
