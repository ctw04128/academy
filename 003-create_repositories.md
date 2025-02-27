# Data access layer

At this point you should have your database connection configured and ready to use as a mapping between your entities and the database tables.

Now you can start creating the data access layer. There are different patterns that you can use for this purpose, like [Active Record](https://en.wikipedia.org/wiki/Active_record_pattern) or [Repository Pattern](https://www.geeksforgeeks.org/repository-design-pattern).

Quarkus provides a [Panache](https://quarkus.io/guides/hibernate-orm#panache) implementation that allows you to manage your data without having to write SQL queries by yourself.


## Configuration

Add the necessary dependencies to your pom
   ````
   <dependency>
        <groupId>io.quarkus</groupId>
        <artifactId>quarkus-hibernate-orm-panache</artifactId>
    </dependency>
   ````

## Exercise

In the exercise you will create a data access layer that manages the persistence of the entities. In this case we'll use the repository pattern.

* Create the necessary Repositories for each entity.

