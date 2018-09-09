# Spring-boot-H2-Hazelcast [![Build Status](https://travis-ci.org/mohasaid/spring-boot-h2-hazelcast.svg?branch=master)](https://travis-ci.org/mohasaid/spring-boot-h2-hazelcast) [![codecov](https://codecov.io/gh/mohasaid/spring-boot-h2-hazelcast/branch/master/graph/badge.svg)](https://codecov.io/gh/mohasaid/spring-boot-h2-hazelcast)

This app is a service which listens to HTTP requests by port 8080 of the different plugins that are distributed in different devices around the world, and responds
a unique code that identifies the view that the app should show, the ping time that the device uses and the host to which you must send all the plugin information. 
The main feature of the service is to respond very fast and to withstand as many concurrent calls as possible. 

Going into details, the service identifies the values of the request:

- Account to which belongs 
- Type of device (For example: Panasonic, Xbox, etc.)
- Plugin version

The first thing that the service has to do when a request is made is to check if the account belongs to the platform, if it does it will continue
with its execution, otherwise will return an empty response.

Second, check whether the device sent is valid for the defined account. Same as before, if it is a valid device will continue with the execution, otherwise
will return an empty response.

The plugin version it will be used to define the ping time value that will be sent to the plugin. 

Finally, the plugin version serves to define the output type that the plugin expects, but in this case I will omit it, I will always return the same 
type of response, which will be an XML with the following form:

```
<?xml version='1.0' encoding='UTF-8'?>
<q>
    <h>clusterA.com</h>
    <pt>5</pt>
    <c>7xnj85f06yqswc5x</c>
</q>
```

The value of the tag **h** defines the host (or cluster) to which all the information will be sent by the plugin, the **pt** tag defines the ping time 
(which by default will be 5, but will depend on the plugin version), and finally the tag **c** defines the code of the view (unique code).

The configuration of the service could be something like the following:

![](https://i.gyazo.com/5dd0f7e82cd8c0fcdb96a940894ff378.png)

In this case, to balance the traffic in different clusters, when _clienteA_ uses the _XBox_ device with the plugin version _3.3.1_, the service
will return a ping time of 10 seconds and it will balance the response between two clusters: clusterA.com (70% requests) and clusterB.com (30% requests).

The same way, when a request is performed by _clienteB_ for the device _osmf_ and the plugin version 3.3.1, the service will return a lower ping time 
(5 seconds in this case), and traffic will be balanced between two clusters with the same weight: clusterA.com and clusterB.com 
(managing 50% of the traffic each of them).


### Prerequisites
* JDK 1.8+
* Maven 3.0+ 

### Build

To build the project yourself you need to clone the project and then run: 

```
$ mvn package
```
### Usage

To run the project:

```
$ java -jar test.jar
```
### Techonologies 

- [Spring Boot](https://spring.io/projects/spring-boot)
- [H2](http://www.h2database.com/html/main.html)
- [Hazelcast](https://hazelcast.com/)

It is built using *spring boot* with *H2* as an embedded database. In order to cache the database requests, it uses *hazelcast*. 
Embedded database is initialized with predefined data that is inserted when the service starts and it can be found at resources folder. 
Nevertheless you can modify the database data when the server is running up by the endpoints 
or just querying the embedded database which is running on *http://localhost:8080/h2-console/* with the credentials set at applications.properties file.

### Documentation 

To know more about API endpoints you can visit the following URL in your browser which displays the documentation (the base path / redirects to it):

http://localhost:8080/swagger-ui.html

### Demo

You can find here a [live demo](https://spring-boot-h2-hazelcast.herokuapp.com/) deployed.


