# AIGS Spring Backend Dockerfile

FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /aigs-spring-server
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-slim
WORKDIR /aigs-spring-server
COPY --from=build /aigs-spring-server/target/*.jar app.jar

EXPOSE 50005
CMD ["java", "-jar", "app.jar"]