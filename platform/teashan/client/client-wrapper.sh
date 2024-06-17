#!/bin/sh

#
# Wrapper script for leshan-client-demo.jar
#
# - Downloads the demo client JAR and the object specifications if needed
# - Provides basic configuration
#
# Enable/disable objects with the client shell:
#   create 32769, create 32770, delete 6, delete 3303, delete 3442
#
# See https://github.com/smartuni/teamagochi/tree/main/node/code/dev/lwm2m/demoServer
#

if [ ! -f ./leshan-client-demo.jar ]; then
    wget https://ci.eclipse.org/leshan/job/leshan-ci/job/master/lastSuccessfulBuild/artifact/leshan-client-demo.jar
fi

if [ ! -d ./models ]; then
    mkdir models
    pushd models
    wget https://raw.githubusercontent.com/smartuni/teamagochi/main/platform/data/objectmodels/32769.xml
    wget https://raw.githubusercontent.com/smartuni/teamagochi/main/platform/data/objectmodels/32770.xml
    popd
fi

USE_BOOTSTRAP=true
MODELS_FOLDER=models
ENDPOINT_NAME=teamagochi-java-client-0

if [ "$USE_BOOTSTRAP" = true ]; then
    # With bootstrap server
    SERVER_URL=coap://localhost:5683
    java -jar ./leshan-client-demo.jar \
        --models-folder="$MODELS_FOLDER" \
        --server-url="$SERVER_URL" \
        --endpoint-name="$ENDPOINT_NAME" \
        "$@"
else
    # Without bootstrap server
    SERVER_URL=coap://localhost:5783
    java -jar ./leshan-client-demo.jar \
        --models-folder="$MODELS_FOLDER" \
        --server-url="$SERVER_URL" \
        --endpoint-name="$ENDPOINT_NAME" \
        --bootstrap \
        "$@"
fi
