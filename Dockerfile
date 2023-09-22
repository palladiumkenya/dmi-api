FROM eclipse-temurin:17.0.7_7-jdk-alpine
MAINTAINER paul.nthusi@thepalladiumgroup.com
COPY target/dmi-api-0.0.1-SNAPSHOT.jar dmi-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/dmi-api-0.0.1-SNAPSHOT.jar"]