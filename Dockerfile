# Build

FROM gradle:7.3.0-jdk17 AS builder

WORKDIR /home/gradle/app-build
COPY build.gradle ./
COPY src ./src
RUN gradle build jar

# Target

FROM openjdk:17

COPY --from=builder /home/gradle/app-build/build/libs/*.jar ./app.jar
ENTRYPOINT ["java", "-jar","./app.jar"]
