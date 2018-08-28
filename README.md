## Java TechTest NPAW

This project is the tech test for **Nice People At Work (NPAW)**.

### Prerequisites
* JDK 1.8+
* Maven 3.0+ 

### Build

To build the project yourself you need to run: 

```
$ mvn package
```
### Usage

```
$ java -jar tech_test_NPAW.jar
```
### Techonologies 

- [Spring Boot](https://spring.io/projects/spring-boot)
- [H2](http://www.h2database.com/html/main.html)
- [Hazelcast](https://hazelcast.com/)

It is built using *spring boot* as web container with a *H*2 as an embedded database. In order to cache the requests, it uses *hazelcast*. That project runs on port 8080 and has three endpoints with its parameters:

```
GET
Returns the view code, ping time and host where the plugin has to send the information
/getData
- accountCode
- targetDevice
- pluginVersion
Example: http://localhost:8080/getData?accountCode=XXX&targetDevice=YYY&pluginVersion=ZZZ
```

```
POST
Adds an entity to the database
/addData
- accountCode
- targetDevice
- pluginVersion
- pingTime
- hosts (It is represented with JSONArray, example: [{"name":"host-test","load":10}])
Example: http://localhost:8080/addData?accountCode=XXX&targetDevice=YYY&pluginVersion=XXX&pingTime=10&hosts= %5B{%22name%22:%22host-test%22,%22load%22:10}%5D]
```

```
POST
Deletes an entity from the database
/deleteData
- accountCode
- targetDevice
- pluginVersion
Example: http://localhost:8080/deleteData?accountCode=XXX&targetDevice=YYY&pluginVersion=ZZZ
```

You can modify the database data by the endpoints or just querying the embedded database which is running on *http://localhost:8080/h2-console/* with the credentials set at applications.properties file.