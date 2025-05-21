FROM openjdk:17-jdk-alpine

LABEL maintainer="pemponce"

ARG JAR_FILE=target/UserAPI-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} UserAPI.jar

ENTRYPOINT ["java","-jar","/UserAPI.jar"]