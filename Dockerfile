FROM openjdk:8-jre-alpine

ADD ./build/libs/easycrypto-0.2.0.jar /easycrypto.jar

CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/easycrypto.jar"]