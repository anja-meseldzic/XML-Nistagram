FROM maven:3.6.3-ibmjava-8-alpine AS appBuild

WORKDIR /usr/src/agent
COPY . .
RUN mvn package -DskipTests

FROM openjdk:8-jdk-alpine AS appRuntime
WORKDIR /app
COPY --from=appBuild /usr/src/agent/target/agent-0.0.1-SNAPSHOT.war ./
EXPOSE 8081
CMD java -jar agent-0.0.1-SNAPSHOT.war