FROM openjdk:11-jdk
ADD *.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=staging", "-jar","/app.jar"]
EXPOSE 8080