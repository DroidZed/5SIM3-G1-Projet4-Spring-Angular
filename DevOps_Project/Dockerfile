FROM eclipse-temurin:11-jdk-alpine as build

LABEL authors="DroidZed"

WORKDIR /workspace/app

RUN apk add --no-cache maven

COPY . .

RUN mvn install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:11-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","tn.esprit.devops_project.DevOps_ProjectSpringBootApplication"]