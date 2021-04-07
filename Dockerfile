FROM openjdk:15-jdk-alpine

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} employerservice.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/employerservice.jar"]