FROM openjdk:11-jre-slim

RUN mkdir /opt/api-movies-example

WORKDIR /opt/api-movies-example

COPY ./target/api-movies-example*.jar api-movies-example.jar

SHELL ["/bin/bash","-c"]

EXPOSE 8080
EXPOSE 5005

ENTRYPOINT java ${ADDITIONAL_OPTS} -jar api-movies-example.jar
