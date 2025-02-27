# REST API

In our system we need to represent a Rack, a Team to whom the Rack belongs, a Team Member that is the responsible for
booking a Rack and the Booking itself that is performed by a Team Member on a Rack.

To maintain the system and use the Rack Booking System, we'll create REST endpoints for the basic operations.

Quarkus offers usefull tools to implement REST endpoints. Check the documentation [here](https://quarkus.io/guides/rest).

In the application previously created you have an example (very simple) of a REST endpoint:

![resource-example](../../assets/resource-example.png)

## Create the REST endpoints

At this point you don't have a database to persist the data, so like in the previous exercice, '03-oop-java101', you can create an in-memory database that will later be replaced by a database, so prepare your code to be simple to refactor.

1. For each representation, create the following package structure
   ```
   com.ctw.workstation
      |
      | 'representationName'
         | 
         | entity 
         | boundary
   ```

   You should end with something similar to this structure
   ```
   com.ctw.workstation
      | booking
         | entity 
         | boundary
      | rack
         | entity 
         | boundary
      | team
         | entity 
         | boundary
      | teammember
         | entity 
         | boundary
   ```
2. Create a Resource class for each representation and place it under the corresponding boundary package

3. Create a rest endpoint that adds a Rack to the system. Below you can check a request and response example.

```
REQUEST:
   URL: http://localhost:8080/workstation/racks
   VERB: POST
   BODY:
      {
         "serialNumber": "1000-12021-01",
         "status": "Active",
         "teamId": 1
      }

RESPONSE:
   STATUS: 201 CREATED
   BODY:
      {
         "id": "32146",
         "serialNumber": "1000-12021-01",
         "status": "Active",
         "teamId": 1
      }
```

4. Create a rest endpoint that adds a Team to the system. Below you can check a request and response example.

```
REQUEST:
   URL: http://localhost:8080/workstation/teams/
   VERB: POST
   BODY:
      {
         "name": "Transformers",
         "product": "Omniverse"
      }

RESPONSE:
   STATUS: 201 CREATED
   BODY:
      {
         "id": "323",
         "name": "Transformers",
         "product": "Omniverse"
      }
```

5. Create a rest endpoint that adds a Booking to the system. Below you can check a request and response example.

```
REQUEST:
   URL: http://localhost:8080/workstation/bookings/
   VERB: POST
   BODY:
      {
         "rackId": "23",
         "requesterId": "1",
         "bookFrom": "2016-03-16T13:00:00.492",
         "bookTo": "2016-03-17T14:00:00.000"
      }

RESPONSE:
   STATUS: 201 CREATED
   BODY:
      {
         "id": "59",
         "rackId": "23",
         "requesterId": "1",
         "bookFrom": "2016-03-16T13:00:00.492",
         "bookTo": "2016-03-17T14:00:00.000"
      }
```

6. Create a rest endpoint that retrieves a list of all racks. Below you can check a request and response example.

```
REQUEST:
   URL: http://localhost:8080/workstation/racks/
   VERB: GET
RESPONSE:
   STATUS: 200 OK
   BODY:
      {
         "racks": [
            {
               "id": "32146",
               "serialNumber": "1000-12021-01",
               "status": "Active",
               "teamId": 1
            },
            {
               "id": "32147",
               "serialNumber": "1000-12021-02",
               "status": "Active",
               "teamId": 3
            },
            {
               "id": "32148",
               "serialNumber": "1000-12021-03",
               "status": "Active",
               "teamId": 2
            }
         ]
      }
```

7. Create a rest endpoint that retrieves a rack by id. Below you can check a request and response example.

```
REQUEST:
   URL: http://localhost:8080/workstation/racks/32148
   VERB: GET
RESPONSE:
   STATUS: 200 OK
   BODY:
      {
         "id": "32148",
         "serialNumber": "1000-12021-03",
         "status": "Active",
         "teamId": 2
      }
```

8. Create a rest endpoint that updates a rack by id.

9. Create a rest endpoint that deletes a rack by id.

## Optional Exercise:

1. Add a query parameter to the list endpoint that returns all racks of a specific status. Below you can check a request
   and response example.

```
REQUEST:
   URL: http://localhost:8080/workstation/racks/racks?status=active
   VERB: GET
RESPONSE:
   STATUS: 200 OK
   BODY:
      {
         "vehicles": [
            {
               "id": "32148",
               "serialNumber": "1000-12021-03",
               "status": "Active",
               "teamId": 2
            }
         ]
      }
```

2. Create a rest endpoint for the remaining crud operations for all entities.
