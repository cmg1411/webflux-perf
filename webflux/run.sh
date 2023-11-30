#!/bin/sh

set +e
export HOST_IP=$(ipconfig getifaddr en0)
cat <<EOF > Dockerfile
FROM openjdk:17-jdk
COPY build/libs/webflux-0.0.1-SNAPSHOT.jar webflux.jar
ENTRYPOINT java -jar webflux.jar
EOF
docker compose down
docker rmi webflux-webflux
set -e
./gradlew clean build
set +e
docker compose up -d