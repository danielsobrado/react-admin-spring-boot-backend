# React Admin Demo + Java Spring Boot/JWT/MySQL REST Backend

This is backend example for the Marmelab's demo of the [react-admin](https://github.com/marmelab/react-admin) library for React.js. It creates a working administration for a fake poster shop named Posters Galore. 

You can test it online at http://marmelab.com/react-admin-demo.

The backend inspired on the work of:
* [zifnab87](https://github.com/zifnab87) on https://github.com/zifnab87/react-admin-demo-java-rest 
* https://github.com/Nooul/spring-boot-rest-api-helpers

Work In Progress: Adapt Auth and Data providers to make use of Spring Boot Backend.

The following instructions describe how to build and deploy this example:
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

## SWAGGER / Open API 3

The UI for swagger can be found on: [swagger](http://localhost:8080/swagger-ui/index.html)

The OpenAPI configuration is found on **`application.properties`**:

```properties
### Swagger / Open API
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.enabled=true 
```

** Note: ** In case of issues with the browser hanging, use it in a incognito session. (It might be related to [circular dependencies](https://stackoverflow.com/questions/35605427/swagger-ui-freezes-after-api-fetch-and-browser-crashes). )

## How to run: Front-End

The react-admin Front-End is available in the /frontend folder, you can compile it with:

```
yarn install
```

And start it with:

```
yarn start
```

WIP

## How to run: Back-End

The Spring Boot backend is available in the /backend folder, you can compile it with:

```
mvn spring-boot:run
```

WIP

## Run as a Docker container

You will need to have git installed as well as Docker.

```bash
git clone https://github.com/danielsobrado/react-admin-spring-boot-backend
cd react-admin-spring-boot-backend
docker build -t react-admin-spring-boot-backend frontend
```

Start the Docker container by binding the front-end to an external port.

```bash
docker run -d --name=frontend -p 3000:3000 frontend
```

WIP