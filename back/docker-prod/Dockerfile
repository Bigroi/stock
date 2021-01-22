FROM openjdk:11-jdk
ADD *.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar","/app.jar"]
EXPOSE 8080