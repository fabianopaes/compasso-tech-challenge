FROM openjdk:8-jdk-alpine

ARG SPRING_PROFILES_ACTIVE
ARG STARTUP_SLEEP
ARG JAVA_OPTS
ARG PORT

ENV SPRING_PROFILES_ACTIVE ${SPRING_PROFILES_ACTIVE:-docker}
ENV STARTUP_SLEEP ${STARTUP_SLEEP:-2}
ENV JAVA_OPTS ${JAVA_OPTS:-'-Xmx512m'}
ENV DEBUG_OPTS ${DEBUG_OPTS}
ENV PORT ${PORT:-8083}

ADD /build/libs/compasso-rest-api-1.0.0.jar /compasso-rest-api.jar

VOLUME /tmp

RUN sh -c 'touch /app.jar'

EXPOSE ${PORT}

CMD echo "The application will start in ${STARTUP_SLEEP}s..." && \
    sleep ${STARTUP_SLEEP} && \
    java ${JAVA_OPTS} ${DEBUG_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /compasso-rest-api.jar
