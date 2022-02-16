# React Admin Demo + Java Spring Boot/JWT/MySQL REST Backend

This is backend example for the Marmelab's demo of the [react-admin](https://github.com/marmelab/react-admin) library for React.js. It creates a working administration for a fake poster shop named Posters Galore. 

You can test it online at http://marmelab.com/react-admin-demo.

The backend inspired on the work of:
* [zifnab87](https://github.com/zifnab87) on https://github.com/zifnab87/react-admin-demo-java-rest 
* https://github.com/Nooul/spring-boot-rest-api-helpers

Work In Progress: Adapt Auth and Data providers to make use of Spring Boot Backend.

## MySQL setup

Install MySQL and create the demo database and user required for this example to run:

```sql
CREATE DATABASE demo;
USE demo;
CREATE USER 'demo'@'%' IDENTIFIED BY 'demo';
GRANT ALL PRIVILEGES ON * . * TO 'demo'@'%';
FLUSH privileges;
```

## Configuration

The backend properties can be configured on **`application.properties`**:

Use the property **`react-admin-api.use-snake-case = true`** if the front end is using the snake case convention for the field names, that is the case in this example.

The MySQL configuration for Hibernate in this case version 8 is also found on:

```properties
spring.jpa.hibernate.naming-strategy = org.hibernate.cfg.ImprovedNamingStrategy
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.dialect.storage_engine=innodb 
```

## How to run (Frontend)

```
yarn start
```

WIP

## Back-End

```
mvn spring-boot:run
```

WIP

