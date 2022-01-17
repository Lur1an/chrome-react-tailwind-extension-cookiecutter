FROM openjdk:17

RUN mkdir /app

COPY build/libs/backend-0.0.1-SNAPSHOT.jar /app
COPY build/resources /app

WORKDIR /app

CMD ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8083","-jar","backend-0.0.1-SNAPSHOT.jar"]