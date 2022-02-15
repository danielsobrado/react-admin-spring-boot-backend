# React Admin Demo + Java Spring Boot/JWT/MySQL REST Backend

This is a demo of the [react-admin](https://github.com/marmelab/react-admin) library for React.js. It creates a working administration for a fake poster shop named Posters Galore. You can test it online at http://marmelab.com/react-admin-demo.

Backend inspired on the work of [zifnab87](https://github.com/zifnab87) on https://github.com/zifnab87/react-admin-demo-java-rest and https://github.com/Nooul/spring-boot-rest-api-helpers

Work In Progress: Adapt Auth and Data providers to make use of Spring Boot Backend.

## MySQL setup

```
CREATE DATABASE demo;
use demo;
CREATE USER 'demo'@'%' IDENTIFIED BY 'demo';
GRANT ALL PRIVILEGES ON * . * TO 'demo'@'%';
FLUSH privileges;
```
WIP

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

