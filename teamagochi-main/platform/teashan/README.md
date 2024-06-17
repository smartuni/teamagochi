# Tea:shan

The **Tea**magochi Le**shan** setup. Packages the demo servers which serve the official
[Test Server Sandbox](https://github.com/eclipse-leshan/leshan#test-server-sandbox).

The Image is based on the official [Eclipse Temurin](https://hub.docker.com/_/eclipse-temurin) image.
For "student project simplicity reasons" everything is bundled in a single container
by using [supervisor](http://supervisord.org/configuration.html) as recommended
in the Docker [Multi-service container documentation](https://docs.docker.com/config/containers/multi-service_container/).

## Get started

### Build

Building the container image requires [BuildKit](https://docs.docker.com/build/buildkit/)
which is the default since Docker version 23.0.

```
docker build -t teashan .
```

Utilizes [multi-stage builds](https://docs.docker.com/build/building/multi-stage).

## Run

Use [`docker run`](https://docs.docker.com/reference/cli/docker/container/run), suprise, suprise.

```
docker run --rm --p 8080:80 --name teashan-servers teashan
```

And open your browser on:

- <http://localhost:8080>
- <http://localhost:8080/bs>

Finally you might want shell access (during development!):

```
docker exec -it teashan-servers /bin/bash
```

### Configuration

The `LESHAN_CMD` and `LESHAN_BS_CMD` environment variables can be used to configure Leshan.

See the `./Dockerfile` to learn about the current defaults.

### Disable NGINX

The reverse proxy can be disabled with the `NGINX_ENABLE` environment variable (default: `true`):

```
docker run --rm --env NGINX_ENABLE=false --name teashan-servers teashan
```

### Disable Bootstrap Server

The bootstrap server can be disabled with the `LESHAN_ENABLE_BS` environment variable:

```
docker run --rm --env LESHAN_ENABLE_BS=false --name teashan-servers teashan
```

## About Eclipse Leshan
[Website](https://eclipse.dev/leshan/) - [Github](https://github.com/eclipse-leshan/leshan)

Eclipse Leshan is an OMA Lightweight M2M server and client Java implementation. They provide
demo servers which we use for our project. Note that these demo servers are
[a working toy \[and\] generally \[don't\] fit production use cases](https://github.com/eclipse-leshan/leshan/issues/789#issuecomment-576732936)!

They host the demos here:

- <https://leshan.eclipseprojects.io/>
- <https://leshan.eclipseprojects.io/bs/>
