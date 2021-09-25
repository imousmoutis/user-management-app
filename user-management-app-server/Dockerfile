FROM adoptopenjdk/openjdk14:alpine-jre

MAINTAINER imousmoutis

ARG JAR_FILE

COPY ${JAR_FILE} app.jar

COPY wait-for-it.sh wait-for-it.sh

EXPOSE 8080

RUN apk update && apk add bash

ENTRYPOINT ["./wait-for-it.sh", "user-management-app-db:3306", "--", "java", "-jar", "app.jar"]
