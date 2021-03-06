FROM openjdk:15-ea-slim-buster

RUN apt-get update \
    && DEBIAN_FRONTEND=noninteractive apt-get -y upgrade \
    && rm -rf /var/lib/apt/lists/*

EXPOSE 8081
COPY  /target/admin-0.0.1-SNAPSHOT.jar /admin/
WORKDIR /admin/

ENTRYPOINT java -jar admin-0.0.1-SNAPSHOT.jar