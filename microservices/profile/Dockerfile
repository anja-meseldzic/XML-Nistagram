FROM maven:3.6.3-ibmjava-8-alpine AS appBuild

WORKDIR /usr/src/profile
COPY . .
RUN mvn package -DskipTests

FROM openjdk:8-jdk-alpine AS appRuntime
WORKDIR /app
COPY --from=appBuild /usr/src/profile/target/profile-0.0.1-SNAPSHOT.war ./
EXPOSE 8080
CMD java -jar profile-0.0.1-SNAPSHOT.war
