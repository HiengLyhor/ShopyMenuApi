FROM eclipse-temurin:24-jdk-alpine
VOLUME /tmp
COPY targer/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080