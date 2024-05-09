#!/bin/bash
set -e

# Create keycloak user and database
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
	CREATE USER ${POSTGRES_KC_USER} WITH PASSWORD '${POSTGRES_KC_PASSWORD}';
	CREATE DATABASE ${POSTGRES_KC_DB};
	GRANT ALL PRIVILEGES ON DATABASE ${POSTGRES_KC_DB} TO ${POSTGRES_KC_USER};
EOSQL

# Import keycloak database
keycloak_dump_file=/docker-entrypoint-initdb.d/keycloak-db.dump
if [ -f "$keycloak_dump_file" ]; then
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_KC_DB" < "$keycloak_dump_file"
fi

# Be sure the schema exists
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  \connect keycloak;
  CREATE SCHEMA IF NOT EXISTS keycloak AUTHORIZATION ${POSTGRES_KC_USER};
EOSQL
