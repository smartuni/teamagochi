#!/bin/bash
openssl genrsa -out ./data/ssl/certs/server.key 2048
openssl req -new -out ./data/ssl/certs/server.csr -key ./data/ssl/certs/server.key -config ./data/ssl/openssl.cnf
openssl x509 -req -days 3650 -in ./data/ssl/certs/server.csr -signkey ./data/ssl/certs/server.key -out ./data/ssl/certs/server.crt -extensions v3_req -extfile ./data/ssl/openssl.cnf

#openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout ./data/nginx.key -out ./data/ssl/nginx.crt
