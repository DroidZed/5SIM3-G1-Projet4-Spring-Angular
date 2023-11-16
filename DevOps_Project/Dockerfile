FROM openjdk:11
EXPOSE 8082
RUN curl -o 5sim1-g1-projet4-spring-angular.jar http://192.168.33.10:8081/repository/maven-releases/pom/groupId/springproject/v1/springproject-v1.jar
ENTRYPOINT ["java", "-jar", "5sim1-g1-projet4-spring-angular.jar"]
