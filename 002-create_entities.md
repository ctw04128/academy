# JPA Entities

In this exercise we will create the necessary Entities that will be used in our Rack Booking System. 

To manage database access we will use [Hibernate ORM](https://quarkus.io/guides/hibernate-orm).
Quarkus provides a [Hibernate ORM](https://quarkus.io/guides/hibernate-orm) extension that provides integration with Hibernate ORM.

## Configuration


Add the necessary dependencies to your pom:
   ````
   <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-hibernate-orm</artifactId>
   </dependency>
   ````

Add the following configuration to your `application.yml` file:

```
  hibernate-orm:
    packages: com.ctw.workstation
    validate-in-dev-mode: false
    database:
      generation: none
    log:
      sql: ${hibernate_orm_log_sql:false}
```

## Exercise

1. Create the necessary entities:
    1. Entity Team
    2. Entity TeamMember
    3. Entity Rack
    4. Entity RackAsset
    5. Entity Booking

2. All the entities should have sequence generated Id's
