FROM quay.io/keycloak/keycloak:24.0.3@sha256:0d71412ed56b5f66f7e922efc0c3ff73b3f54ba2fe84fedb5e281a1f90b95bd6 as base

#
# Creates a pre-configured Keycloak image, see https://www.keycloak.org/server/containers
#

# Builder stage
FROM base as builder

## Set build-time configs
ENV KC_CACHE=local
ENV KC_DB=postgres
ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true
ENV KC_TRANSACTION_XA_ENABLED=false
# NOTE: should be a parameter but might never change
ENV KC_HTTP_RELATIVE_PATH=/kc

WORKDIR /opt/keycloak

RUN /opt/keycloak/bin/kc.sh build

# Runtime stage
FROM base

COPY --from=builder /opt/keycloak/ /opt/keycloak/

ENV TZ="Europe/Berlin"

## Preconfigure postgres instance
ENV KC_DB_URL_HOST=postgres
ENV KC_DB_URL_PORT=5432
ENV KC_DB_URL_DATABASE=keycloak
ENV KC_DB_USERNAME=keycloak
ENV KC_DB_SCHEMA=keycloak

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]
