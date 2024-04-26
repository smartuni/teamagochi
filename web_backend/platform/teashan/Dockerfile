#
# Downloader stage
#
FROM eclipse-temurin:21 AS downloader

RUN mkdir /downloads
WORKDIR /downloads

RUN wget https://ci.eclipse.org/leshan/job/leshan-ci/job/master/lastSuccessfulBuild/artifact/leshan-server-demo.jar
RUN wget https://ci.eclipse.org/leshan/job/leshan-ci/job/master/lastSuccessfulBuild/artifact/leshan-bsserver-demo.jar

#
# Runnable stage
#
FROM eclipse-temurin:21

# Install dependencies
#
#  - supervisor, for running multiple processes in a single container
#  - nginx, to proxy requests to the leshan webapps
#
RUN apt-get update && apt-get install --no-install-recommends -yq \
    supervisor \
    nginx \
    && rm -rf /var/lib/apt/lists/*

# Create a designated leshan user and directory
RUN useradd -r -s /bin/false leshan
RUN install -o leshan -d /leshan
WORKDIR /leshan

# Copy all packages, scripts and configuration files
COPY --chmod=700 scripts/docker-entrypoint.sh /usr/local/bin/

COPY etc/supervisord.conf /etc/supervisor/supervisord.conf
COPY etc/supervisord.d/* /etc/supervisor/conf.d

COPY etc/nginx.conf /etc/nginx/nginx.conf
COPY etc/nginx.d/* /etc/nginx/conf.d/

COPY --from=downloader /downloads/leshan-*.jar /leshan

# Define Leshan Server command
ENV LESHAN_CMD /opt/java/openjdk/bin/java -jar /leshan/leshan-server-demo.jar \
    --web-port=8080

# Define Leshan Bootstrap Server command
ENV LESHAN_ENABLE_BS true
ENV LESHAN_BS_CMD /opt/java/openjdk/bin/java -jar /leshan/leshan-bsserver-demo.jar \
    --coap-port=5783 \
    --coaps-port=5784 \
    --web-port=8081

# This doesn't actually publish the port, see
#   https://docs.docker.com/reference/dockerfile/#expose
EXPOSE 80/tcp
EXPOSE 5683/udp
EXPOSE 5684/udp
EXPOSE 5685/udp
EXPOSE 5783/udp
EXPOSE 5784/udp

ENTRYPOINT ["docker-entrypoint.sh"]
CMD ["/usr/bin/supervisord", "-c", "/etc/supervisor/supervisord.conf"]
