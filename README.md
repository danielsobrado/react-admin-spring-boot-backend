# React Admin Demo + Java Spring Boot/MySQL REST Backend

This is a demo of the [react-admin](https://github.com/marmelab/react-admin) library for React.js. It creates a working administration for a fake poster shop named Posters Galore. You can test it online at http://marmelab.com/react-admin-demo.

[![react-admin-demo](https://marmelab.com/react-admin/img/react-admin-demo-still.png)](https://vimeo.com/268958716)

React-admin usually requires a REST/GraphQL server to provide data. In this bundle an exmaple Java spring boot REST api implementation is provided  in the /backend folder

To explore the source code, start with [src/App.js](https://github.com/marmelab/react-admin/blob/master/examples/demo/src/App.js).

**Note**: This project was bootstrapped with [Create React App](https://github.com/facebookincubator/create-react-app).

## How to run (Frontend)

After having cloned the repository, run the following commands at the repository root:

```sh
make install

make build

make run-demo
```

## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.<br>
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.<br>
You will also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.<br>
See the section about [running tests](#running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.<br>
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.<br>
Your app is ready to be deployed!

### `npm run deploy`

Deploy the build to GitHub gh-pages.

## Back-End

This backend implementation is the result of considerable effort to use admin-on-rest as front-end but migrate away from Headless Drupal backend (PHP) to Java Spring backend/MySQL. The main reasons including: lack of versioning for backend changes (we had to take data dumps and keep a txt with Drupal changes), time consuming configuration of Views involving many entities and fields, in need of many (some times non-existing) Plugins to do common things, not native REST implementation, queries involving a ton of tables due to Drupal field reusability among different nodes, difficulty combining drupal tables with flat tables for big-data and analytics, etc..

## Configuration

You need a database called demo. The credentials are being configured in application.properties. Open the project using existing resources and select Maven from IntelliJ Idea menu. Run ReactAdminDemoApplication.java that will start a Java Spring Boot Application on http://localhost:8080

### Features

- Automatic Generation of database tables according to the Java classes annotated with `@Entity`
- Automatic filling of data from https://raw.githubusercontent.com/zifnab87/react-admin-demo-java-rest/master/backend/src/main/webapp/WEB-INF/uploaded/data.json
- Rest API based on admin-on-rest conventions (e.g resource names and calling signatures: https://marmelab.com/admin-on-rest/RestClients.html)
- Built-in User Authentication (followed this implementation: https://auth0.com/blog/securing-spring-boot-with-jwts/)
- Easily expandable by adding a new `@Entity` class, extending `BaseRepository<T>`, extending `BaseController<T>` both provided by https://github.com/zifnab87/react-admin-java-rest
- Paging and Sorting behind the scenes support using `PagingAndSortingRepository` provided by Java Spring-Data
- Text Search among all text fields using q parameter 
- Exact Match filtering among the rest of the fields of a resource
- All query building is happening behind the scenes using Specifications and Criteria API provided by Java Spring-Data
- Ability to support snake_case or camelCase variables by setting (`react-admin-api.use-snake-case = true` (default = false)) in application.properties
- Automatic wrapping of responses in "content" using `@ControllerAdvice` provided by https://github.com/zifnab87/react-admin-java-rest/blob/master/src/main/java/reactAdmin/rest/controllerAdvices/WrapperAdvice.java
- Automatic calculation total number of results returned and addition of that number in `X-Total-Count` response header provided as `@ControllerAdvice` by https://github.com/zifnab87/react-admin-java-rest/blob/master/src/main/java/reactAdmin/rest/controllerAdvices/ResourceSizeAdvice.java
- Automatic deserialization of entities by their ids only during POST/PUT, using `@JsonCreator` annotations over constructors of Entities - see here for explanation: https://stackoverflow.com/questions/46603075/single-custom-deserializer-for-all-objects-as-their-ids-or-embedded-whole-object


### Future work

- Make the project runnable through Maven - currently it is a IntelliJ Idea Maven project
- ~~Be able to combine results from Text Search and Exact Match filtering~~ **DONE**
- Indexes that might be missing currently
- Swagger-UI needs to be excluded properly from authentication



