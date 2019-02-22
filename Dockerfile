FROM openjdk:8-jre-alpine

RUN apk update && apk add bash

EXPOSE 5005

ADD ./build/libs/easycrypto-0.2.0.jar /easycrypto.jar
ADD ./src/main/resources/GeoLite2-City.mmdb /GeoLite2-City.mmdb

CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "/easycrypto.jar"]