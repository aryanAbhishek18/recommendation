# Car recommendation system

## Tech Stack
* Java
* SpringBoot
* PostgreSQL
* Docker


## Setup

1. **Seting up containerized postgres DB**
```
docker pull postgres

docker run -d --name postgresContainer -p 5432:5432 -e POSTGRES_PASSWORD=password123 postgres
```

So, now the postgres container is running and we can check using `docker ps`.

2. **Creating the `car_db` in our postgres container**
```
# now to bash inside the postgres container
docker exec -it postgresContainer bash

# to connect to postgres shell inside the container
psql -h localhost -U postgres

# now we can execute psql commands:)

# view databases
\l

# create database
CREATE DATABASE car_db;

# switch to car_db database
\c car_db

# view the relations/tables inside the current db
\dt

# find all records from relation car
select * from car;
```

3. **Basic `application.properties` file content to connect to postgres**
```
spring.application.name=Car Recommendation System

## PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/car_db
spring.datasource.username=postgres
spring.datasource.password=password123

# create and drop table, good for testing, production set to none or comment it
spring.jpa.hibernate.ddl-auto=update

# Disable Hibernate usage of JDBC metadata
spring.jpa.properties.hibernate.boot.allow_jdbc_metadata_access=false

# Specify explicitly the dialect (here for PostgreSQL, adapt for your database)
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```


## Functional Requirements
The Comparison module should:
* Suggest upto 10 cars that are similar to car being viewed by the user
* Allow the user to select upto 2 other cars for comparison from the suggested list in addition to the selected car
* Display the feature and specifications comparison table
* Provide an option to the user to see only the differences and hide the similar features


## Non-Functional Requirements
* **Usability**:
    System should have intuitive API endpoints that reflect their purpose.
    The APIs should be easy to use for the user.


* **Maintainability**:
The System should be implemented using a well-organised codebase.
Code should be reused, easy to understand, modify and extend for future use cases.


* **Scalability**:
The APIs should handle growing traffic without losing functionality and performance.
The system should ensure low latency for the API calls using caching.


* **Availability**:
The APIs should allow users to research about cars whenever they want.
System downtime should be minimal.


## Score Generation Algorithm
The score for each car is generated once in a WEIGHTED SUM fashion at the time it is created taking help of its QUANTIFIABLE properties like:
* Year
* Seating
* Horsepower
* Mileage
* Price

Also, we have the Weight table which stores the thresholds for each of the quantifiable properties based on the vehicle TYPE.

In short, score of a car = Summation (quantifiableProperty * correspondingWeight)


## Significance of Score
* The score is what helps the system in determining which cars to be recommended.
* The scores once computed are not changed as the quantifiable attributes of an existing old car model won’t change with time.
* That being said, if however we decide to change the weight thresholds for the quantifiable attributes would require a recalculation of scores.
* The Car Recommendation Algorithm internally uses the car scores to determine the most matching cars and depending on the limit of recommendations (let’s say n) returns the n most recommended cars to the user. 


## Recommendation Algorithm
* The recommendation algorithm always recommends cars from the same TYPE.
Eg - a user demanding recommendations for a sedan would always see recommendations of other sedans
Similarly, for hatchbacks and suvs.
* Keeping the input car’s score as a baseline, we analyze other car’s scores of the same type.
* Going into a bit of details, we specifically check the absolute difference of other car’s scores and input score and return the ones which differ least from the input score.
* To further reduce the latency, we also prefetch and cache the cars data to avoid frequent DB calls.
* The cache is also periodically refreshed to avoid staleness.
* Also added a functionality to have a flexible limit on the number of recommendations required.

