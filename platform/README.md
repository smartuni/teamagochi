# The Platform

So what is this platform? In short, it is the integration of all the components of our system.

**But why?**

1. To provide a simple development environment for all teams. You need an identity provider? Here it is. A LightweightM2M Server? No problem either. Have you lost track? Take a look at the dashboard.

2. We will need some of the included services when presenting our project. Since the university has restrictive regulations for the available wireless networks, we cannot use them (IPv6 who's gonna need that? Except these IoT people?). Therefore we put the platform on a Raspberry Pi and work locally.

Hopefully it will serve this purpose...

## Get started

The first start takes some time.

```
docker compose up --build
```

Visit <http://localhost:4000>

For a fresh start, just delete the volumes with `-v`.

```
docker compose down -v
```

### TODO

```
docker compose -f compose.no-backend.yml up --build
docker compose -f compose.no-backend.yml down
```

quarkus dev -Dquarkus.profile=keycloak
quarkus dev -Dquarkus.profile=dev,keycloak,teashan
quarkus dev -Dquarkus.profile=dev,teashan

### Requirements

Unknown.  BuildKit is required. Your need a recent Docker version, I guess. It is tested with:

```
$ docker compose version
Docker Compose version 2.26.1

$ docker --version
Docker version 26.0.0, build 2ae903e86c
```

By default Docker does not support IPv6. It must be enabled with 
a `/etc/docker/daemon.json` like the following example.

```
{
  "experimental": true,
  "ipv6": true,
  "ip6tables": true,
  "fixed-cidr-v6": "fd80:6350:1::/64",
  "default-address-pools": [
    { "base": "172.17.0.0/16", "size": 16 },
    { "base": "172.18.0.0/16", "size": 16 },
    { "base": "172.19.0.0/16", "size": 16 },
    { "base": "172.20.0.0/14", "size": 16 },
    { "base": "172.24.0.0/14", "size": 16 },
    { "base": "172.28.0.0/14", "size": 16 },
    { "base": "192.168.0.0/16", "size": 20 },
    { "base": "fd80:6350::/104", "size": 112 }
  ]
}
```

TODO: `[fd00:dead:beef::1]:5685`

See https://docs.docker.com/config/daemon/ipv6

## Services

### Development Dashboard > Make yourself comfortable
[See ./devsash/README.md](devdash/README.md)

A central entrypoint for this platform.

- Provides a overview of all services and how they can be accessed.
- Allows to login and inspect the Access and Identity-Token.

### Tea:shan > The <u>Tea</u>magochi Le<u>shan</u> setup
[See ./teashan/README.md](teashan/README.md)

Container image containing the _Leshan Test Server Sandbox_,
consisting of a LwM2M server and a Bootstrap server.

### Keycloak > Identity and Access Management
[Website][keycloak_web] - [Documentation][keycloak_docs] - [Container image][keycloak_image]

A open source identity provider, so we don't need to deal with storing users or authenticating users. Keycloak provides user federation, strong authentication, user management, fine-grained authorization, and more.

[keycloak_web]: https://www.keycloak.org/
[keycloak_docs]: https://www.keycloak.org/documentation
[keycloak_image]: https://quay.io/repository/keycloak/keycloak

### NGINX > Reverse Proxy
[Website][nginx_web] - [Documentation][nginx_docs] - [Container image][nginx_image]

NGINX („engine-ex“) is configured to be a **reverse proxy** server and provides a central
entrypoint for all web frontends. It directs client requests to the appropriate backend server.

[nginx_web]: https://nginx.org/en/
[nginx_docs]: https://nginx.org/en/docs/
[nginx_image]: https://hub.docker.com/_/nginx

### Mailpit > Email & SMTP testing tool

[Website][mailpit_web] - [Documentation][mailpit_docs] - [Container image][mailpit_image]

Mailpit is an email & SMTP testing tool with. It acts as an SMTP server, provides a modern web
interface to view & test captured emails, and contains an API for automated integration testing.

[mailpit_web]: https://mailpit.axllent.org/
[mailpit_docs]: https://mailpit.axllent.org/docs
[mailpit_image]: https://hub.docker.com/r/axllent/mailpit

### Postgres > DBMS

[Website][postgres_web] - [Documentation][postgres_docs] - [Container image][postgres_image]

PostgreSQL is a powerful, open source object-relational database.

[postgres_web]: https://www.postgresql.org/
[postgres_docs]: https://www.postgresql.org/docs/15/index.html
[postgres_image]: https://hub.docker.com/_/postgres
