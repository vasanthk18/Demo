FROM openjdk:17
WORKDIR /app
EXPOSE 8082
COPY target/vby-software.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]