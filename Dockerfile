FROM openjdk:17
EXPOSE 8082
ADD target/vby-software.jar vby-software.jar
ENTRYPOINT ["java","-jar","/vby-software.jar"]