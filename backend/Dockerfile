FROM maven:3.6.1-jdk-8-slim AS build
RUN mkdir -p /prueba-dockers
WORKDIR /prueba-dockers
COPY pom.xml /prueba-dockers
COPY src /prueba-dockers/src
RUN mvn -f pom.xml clean package

FROM openjdk:8-alpine
COPY --from=build /prueba-dockers/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]