FROM openjdk:15-jdk-alpine

ARG AUTH_SERVICE_URL=http://localhost:8090/api/auth

ENV AUTH_URL=$AUTH_SERVICE_URL

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} employerservice.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/employerservice.jar"]