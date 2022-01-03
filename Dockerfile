FROM openjdk:11-jre-slim-bullseye
RUN adduser --system --group springdocker
USER springdocker:springdocker
ARG JAR_FILE=app/build/libs/nospring-service-skeleton.jar
COPY ${JAR_FILE} nospring-service-skeleton.jar
ENTRYPOINT ["java","-jar", \
"/nospring-service-skeleton.jar"]
# Environment variables to be prdvided in docker-compose
