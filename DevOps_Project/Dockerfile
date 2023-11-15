FROM eclipse-temurin:11-jdk-alpine as build

LABEL authors="DroidZed"

WORKDIR /workspace/app

RUN apk add --no-cache maven

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:11-jdk-alpine

VOLUME /tmp

COPY --from=build /target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
