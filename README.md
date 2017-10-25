# task-todo 

This is a sample Java / Maven / Spring Boot application.

## How to Run 

This application is packaged as a war which has Tomcat 8 embedded. 
You run it using the ```java -jar``` command.

* Clone this repository 
* Make sure you are using JDK 1.8 and Maven 3.x
* You can build and install the project by running ```mvn clean install```
* Once successfully built, you can run the service by one of these two methods:
```
        java -jar -Dspring.profiles.active=test target/task-todo-0.0.1.war
or
        mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"
```
* Check the stdout or application.log file to make sure no exceptions are thrown

Once the application runs you should see something like this

```
2017-10-25 20:45:45.003  INFO 10292 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8090 (http)
2017-10-25 20:45:45.010  INFO 10292 --- [           main] com.david.tasktodo.Application           : Started Application in 25.306 seconds (JVM running for 26.197)
```

## About the Service

* More to be edited


### To view Swagger 2 API docs

Run the server and browse to localhost:8090/swagger-ui.html

### To view Swagger 2 API docs (OpenAPI) 
For a nicer display please follow these steps:

* Run the server and browse to http://localhost:8090/v2/api-docs
* Copy the JSON content
* Paste it to https://editor.swagger.io
* Visualize the documentation.

# Attaching to the app remotely from your IDE

Run the service with these command line options:

```
mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
or
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -Dspring.profiles.active=test -Ddebug -jar target/task-todo-0.0.1.war
```
and then you can connect to it remotely using your IDE. For example, from IntelliJ You have to add remote debug configuration: Edit configuration -> Remote.

### Questions and Comments: davidvara28@hotmail.com
