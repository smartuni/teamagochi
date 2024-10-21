#!/bin/bash

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
    pushd models || exit 1
    wget https://raw.githubusercontent.com/smartuni/teamagochi/main/platform/data/objectmodels/32769.xml
    wget https://raw.githubusercontent.com/smartuni/teamagochi/main/platform/data/objectmodels/32770.xml
    popd || exit 1
fi

USE_BOOTSTRAP=true
MODELS_FOLDER=models
DEFAULT_ENDPOINT_ID=java-1
ENDPOINT_NAME="urn:t8i:dev:${1:-$DEFAULT_ENDPOINT_ID}"

shift

if [ "$USE_BOOTSTRAP" = true ]; then
    # With bootstrap server
    SERVER_URL=coap://localhost:5683
    #SERVER_URL=coap://teamagochi:5683
    { sleep 5; echo -e "create 32769\n create 32770\n delete 6\n delete 3303\n delete 3442\n"; } \
        | java -jar ./leshan-client-demo.jar \
        --models-folder="$MODELS_FOLDER" \
        --server-url="$SERVER_URL" \
        --endpoint-name="$ENDPOINT_NAME" \
        "$@"
else
    # Without bootstrap server
    SERVER_URL=coap://localhost:5783
    #SERVER_URL=coap://teamagochi:5783
    { sleep 5; echo -e "create 32769\n create 32770\n delete 6\n delete 3303\n delete 3442\n"; } \
        | java -jar ./leshan-client-demo.jar \
        --models-folder="$MODELS_FOLDER" \
        --server-url="$SERVER_URL" \
        --endpoint-name="$ENDPOINT_NAME" \
        --bootstrap \
        "$@"
fi
