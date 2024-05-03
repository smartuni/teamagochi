#!/bin/sh

# Simple script which dumps the Keycloak database from the host.
# The create file can be used to prepare the database when the
# postgres container is initialized. See 'initdb.d' directory.

# NOTE: Set variables before use!
KEYCLOAK_DB=keycloak
POSTGRES_USER=postgres
POSTGRES_PASSWORD=THE_PASSWORD
POSTGRES_CONTAINER=THE_CONTAINER

{ docker exec -i -e POSTGRES_PASSWORD="$POSTGRES_PASSWORD" -e KEYCLOAK_DB="$KEYCLOAK_DB" -u "$POSTGRES_USER" "$POSTGRES_CONTAINER" \
  sh -c 'export PGPASSWORD=$POSTGRES_PASSWORD && pg_dump --clean --if-exists "$KEYCLOAK_DB"' | tee > ./keycloak-db.dump; } \
  2> "./keycloak-db-export.err"

if [ ! -s "./keycloak-db-export.err" ]; then
  rm "./keycloak-db-export.err"
fi
