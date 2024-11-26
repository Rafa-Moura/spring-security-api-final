FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/*.jar spring-security-api.jar
EXPOSE 8080
CMD ["java", "-jar", "spring-security-api.jar"]