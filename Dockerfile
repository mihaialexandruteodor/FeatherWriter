#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS MAVEN_BUILD
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package


#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=MAVEN_BUILD  /home/app/target/featherwriter.jar /usr/local/lib/featherwriter.jar
EXPOSE 8081
VOLUME FeatherWriter
ENTRYPOINT ["java","-jar","/usr/local/lib/featherwriter.jar"]

