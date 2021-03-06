# Using the smallest image there is
FROM openjdk:8-jdk-alpine
# Exposing a port for application
EXPOSE 8080
# Adding a jar_file as a target dep.
ARG JAR_FILE=target/neftrest-1.0.0.jar
# JAR file for executions
ADD ${JAR_FILE} app.jar
# Running as a different group user
RUN addgroup -g 1001 -S 1000 && \
    adduser -u 1001 -S 1000 -G 1000
#
USER 1000
# Entrypoint for our application
ENTRYPOINT ["java","-jar","/app.jar"]
