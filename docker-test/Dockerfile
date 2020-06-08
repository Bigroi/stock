FROM openjdk:11-jdk
ADD *.war app.war
ENTRYPOINT ["java", "-Dspring.profiles.active=staging", "-jar","/app.war"]
EXPOSE 80