# Liquibase

As you probably noticed, we like to automate as much as possible, so we will use [Liquibase](https://www.liquibase.org/) to manage our database schema. Quarkus provides a [Liquibase extension](https://quarkus.io/guides/liquibase) that allow us in a easy way to integrate Liquibase with our application.


Therefore, our goal in these exercises is to prepare all database infrastructure needed for the application.

## Maven Dependencies

Add the Liquibase and JDBC dependency to the application.

   ````
   <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-liquibase</artifactId>
   </dependency>
   
   <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-jdbc-postgresql</artifactId>
   </dependency>
   ````

##  Configuration

In order to your application access the database we need to configure the datasource. Also we need to setup liquibase configuration to run the migrations.

Place the following configuration in `src/main/resources/application.yml`:

```
quarkus:
...

  datasource:
    db-kind: postgresql
    devservices:
      enabled: true
      reuse: false
      image-name: "postgres:14.10-alpine"
      container-env:
        "POSTGRES_DB": "workstation-rack"
        "POSTGRES_USER": "postgres"
        "PASSWORD": "postgres"
      port: 5433
      volumes:
        "db/init-db": "/docker-entrypoint-initdb.d"
      db-name: "workstation-rack"
      username: "postgres"
      password: "postgres"
    jdbc:
      url: jdbc:postgresql://localhost:5433/workstation-rack
      initial-size: 2
      min-size: 2
      max-size: 5

   liquibase:
      migrate-at-start: true
      search-path:
         - "db/changelog"
      change-log: "db/changelog.xml"
```



## Exercise

You should reuse the scripts from previous exercise to setup the database.

`db/changelog.xml` should be created with the necessary liquibase configuration. You can follow the example application.
