FROM maven:3.6.3-ibmjava-8-alpine AS appBuild

WORKDIR /usr/src/media
COPY . .
RUN mvn package -DskipTests

FROM openjdk:8-jdk-alpine AS appRuntime
WORKDIR /app
WORKDIR /app/storage/media-content
WORKDIR /app
COPY --from=appBuild /usr/src/media/target/media-0.0.1-SNAPSHOT.war ./
EXPOSE 8080
CMD java -jar media-0.0.1-SNAPSHOT.war
