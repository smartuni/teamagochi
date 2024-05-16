 set CoAP, CoAPs and webserver ports for bootstrap server
BS_COAPPORT=5685
BS_COAPSPORT=5686
BS_WEBPORT=8888

# run the server
java -jar ./leshan-bsserver-demo.jar --coap-port ${BS_COAPPORT} \
            --coaps-port ${BS_COAPSPORT} --web-port ${BS_WEBPORT} &

java -jar ./leshan-server-demo.jar -m models -lh 2001:db8:1::1 &
