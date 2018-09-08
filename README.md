# Spring-boot-H2-Hazelcast [![Build Status](https://travis-ci.org/mohasaid/spring-boot-h2-hazelcast.svg?branch=master)](https://travis-ci.org/mohasaid/spring-boot-h2-hazelcast)

Simple app for testing spring boot with H2 as embedded database caching database requests and balancing responses.

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
$ java -jar test.jar
```
### Techonologies 

- [Spring Boot](https://spring.io/projects/spring-boot)
- [H2](http://www.h2database.com/html/main.html)
- [Hazelcast](https://hazelcast.com/)

It is built using *spring boot* with *H2* as an embedded database. In order to cache the requests, it uses *hazelcast*. That project runs on port 8080 and has three endpoints with its parameters:

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
Example: http://localhost:8080/addData?accountCode=XXX&targetDevice=YYY&pluginVersion=XXX&pingTime=10&hosts=%5B{%22name%22:%22host-test%22,%22load%22:10}%5D
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

Database is initialized with predefined data that can be found at resources files, but you can modify it when the server is running up by the endpoints or just querying the embedded database which is 
running on *http://localhost:8080/h2-console/* with the credentials set at applications.properties file.