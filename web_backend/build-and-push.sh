#!/bin/bash
echo $TEAMAGOCHI_TOKEN | docker login ghcr.io -u ozfox --password-stdin
./mvnw install -Dquarkus.profile=prod,publish
