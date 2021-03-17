FROM openjdk:8
EXPOSE 8080
COPY target/Quickbuy.jar Quickbuy.jar
ENTRYPOINT ["java", "-jar", "/quickbuy.jar"]
