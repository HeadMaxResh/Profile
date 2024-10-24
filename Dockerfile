FROM amazoncorretto:17
WORKDIR /app
COPY build/libs/*.jar app.jar
COPY src/main/resources/application.properties /app/application.properties
ENTRYPOINT ["java", "-jar", "app.jar"]