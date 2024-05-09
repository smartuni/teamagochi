# Development Dashboard

This is a hacky, copypasta, web application which aims to improve our dev experience:

- It provides a centralized dashboard to get a better overview of the ecosystem
- You can login and analyze the related Access- and ID-tokens

## Get started

```
npm install
npm run dev
```

Visit <http://localhost:3030>

Config file is `/src/config.ts`.  
Environment variables are set in `.env`.

### Build

Building the container image is only tested with [BuildKit](https://docs.docker.com/build/buildkit/)
which is the default since Docker version 23.0.

```
docker build -t devdash .
```

Utilizes [multi-stage builds](https://docs.docker.com/build/building/multi-stage).

### Run

Use [`docker run`](https://docs.docker.com/reference/cli/docker/container/run), suprise, suprise.

```
docker run --rm -p 8080:8080 --name devdash-server devdash
```

And open your browser on:

- <http://localhost:8080>