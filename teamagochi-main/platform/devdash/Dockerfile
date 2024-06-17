FROM node:20.12-bookworm as base

#
# Build stage
#
FROM base as builder

WORKDIR /build

COPY package*.json ./
RUN npm ci

COPY . ./
RUN npm run build

#
# Runnable stage
#
FROM base

WORKDIR /usr/src/app

RUN npm install -g serve

USER node

COPY --from=builder /build/dist /usr/src/app/dist

EXPOSE 8080

CMD ["serve", "-l", "tcp://0.0.0.0:8080", "-s", "./dist"]
