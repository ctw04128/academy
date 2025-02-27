# API Specification - OpenAPI

Now that we have a working RESTful API, we need to define its specification in order to be able to use. For this purpose, we will use [OpenAPI](https://swagger.io/docs/specification/about/), Quarkus provides a [RESTEasy OpenApi implementation](https://quarkus.io/guides/openapi-swaggerui).


## Add Extension

To add the RESTEasy OpenApi extension, we need to add the following dependency:

```
./mvnw quarkus:add-extension -Dextensions='quarkus-smallrye-openapi'
```

In your pom.xml you should now see the following:

```
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-smallrye-openapi</artifactId>
        </dependency>
```

## Exercise time

1. Create an API Specification for the Rack Booking System using OpenAPI.
2. Use the Swagger UI to interact with the API you defined previously.

