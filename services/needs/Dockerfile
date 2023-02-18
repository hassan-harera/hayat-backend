FROM openjdk:17.0.2-slim
COPY ./target/*.jar /project/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/project/app.jar"]