FROM openjdk:8-jre-slim
ADD ./build/libs/book-service.jar .
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/book-service.jar"]