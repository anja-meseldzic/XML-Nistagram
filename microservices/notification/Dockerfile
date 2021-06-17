FROM maven:3.6.3-ibmjava-8-alpine AS appBuild

WORKDIR /usr/src/notification
COPY . .
RUN mvn package -DskipTests

FROM openjdk:8-jdk-alpine AS appRuntime
WORKDIR /app
COPY --from=appBuild /usr/src/notification/target/notification-0.0.1-SNAPSHOT.war ./
EXPOSE 8086
CMD java -jar notification-0.0.1-SNAPSHOT.war
