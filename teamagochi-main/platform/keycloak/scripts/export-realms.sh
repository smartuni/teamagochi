#!/bin/sh

# Simple script which dumps the Keycloak realms from the host.
# The default Keycloak container must be stopped before exporting.
#
# IMPORTANT: Exports via Web-Interface are different! Only use CLI exports...

current_dir="$(basename "$PWD")"
if [ "$current_dir" = "platform" ] && [ -f "compose.yml" ] && [ -d "data/keycloak/export" ]; then
  docker compose stop keycloak
  docker compose run --rm --no-deps \
    -v "${PWD}/data/keycloak/export:/opt/keycloak/data/export" \
    keycloak export --optimized --dir /opt/keycloak/data/export
  docker compose start keycloak
else
  echo "ERROR: Run this script from the 'platform' directory where the 'compose.yml' file is located."
fi
