#!/bin/sh

set +e
export HOST_IP=$(ipconfig getifaddr en0)
cat <<EOF > Dockerfile
FROM openjdk:17-jdk
COPY build/libs/mvc-0.0.1-SNAPSHOT.jar mvc.jar
ENTRYPOINT java -jar mvc.jar
EOF
docker compose down
docker rmi mvc-mvc
set -e
./gradlew clean build
set +e
docker compose up -d