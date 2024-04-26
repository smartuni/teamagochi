<!-- markdownlint-disable-file -->
<div align="center">
  <h1>Teamagochi Web-Backend</h1>

The backend for the Teamgochi project, build with the [Quarkus](https://quarkus.io) Java Framework.<br /><br />
<strong>[Explore the docs](https://github.com/smartuni/teamagochi/tree/main/web_backend)</strong>
</div>

<!-- ~~~~~~~~~~~~~~~~~~~ END OF HEADER ~~~~~~~~~~~~~~~~~~~ -->

## Usage

The development environment can be started by using the [Quarkus CLI](https://quarkus.io/guides/cli-tooling),
the `mvnw.cmd` script (Windows) or the equivalent `./mvnw` (Linux).

```shell
quarkus dev

# Maven
./mvnw compile quarkus:dev
mvnw.cmd compile quarkus:dev
```

Run `quarkus --help`, `./mvnw quarkus --help` or `mvnw.cmd quarkus --help` to display help and all available commands.
Read more about the [development mode](https://quarkus.io/guides/maven-tooling#dev-mode) or
about the [command line interface](https://quarkus.io/guides/cli-tooling) in general.

## Extensions

The following table contains all used [Quarkus Extensions](https://quarkus.io/extensions/)

| Name                       | Links                                                                                                                                    | Why?                                                                               |
|----------------------------|------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| REST                       | [Docs](https://quarkus.io/guides/rest), [Info](https://quarkus.io/extensions/io.quarkus/quarkus-rest)                                    | Create and provide REST services                                                   |
| REST Jackson               | [Docs](https://quarkus.io/guides/rest#json-serialisation), [Info](https://quarkus.io/extensions/io.quarkus/quarkus-rest-jackson/)        | Jackson serialization (JSON) support for REST extension                            |
| REST Client                | [Docs](https://quarkus.io/guides/rest-client), [Info](https://quarkus.io/extensions/io.quarkus/quarkus-rest-client/)                     | Call REST services, support receiving Server-sent events (SSE)                     |
| REST Client Jackson        | [Info](https://quarkus.io/extensions/io.quarkus/quarkus-rest-client-jackson/)                                                            | Jackson serialization (JSON) support for the REST Client                           |
| Hibernate ORM with Panache | [Docs](https://quarkus.io/guides/hibernate-orm-panache), [Info](https://quarkus.io/extensions/io.quarkus/quarkus-hibernate-orm-panache/) | Domain model persistence for relational databases using the the repository pattern |
| JDBC Driver - PostgreSQL   | [Docs](https://quarkus.io/guides/datasource), [Info](https://quarkus.io/extensions/io.quarkus/quarkus-jdbc-postgresql/)                  | Connect to the PostreSQL database via JDBC                                         |
| JDBC Driver - H2           | [Docs](https://quarkus.io/guides/datasource), [Info](https://quarkus.io/extensions/io.quarkus/quarkus-jdbc-h2/)                          | Connect to the H2 database via JDBC                                                |
