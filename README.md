## Button Service

### Specification

https://www.usebutton.com/developers/partner-engineer-coding-challenge/

### Overview

A simple REST-based service & corresponding client website to allow the following:

* User authentication via Facebook (OAuth)
* A search page to allow the user to search:
   * All posts
   * Posts for a particular date (in the format mm/dd/yyyy)
   * Posts containing a matching hash tag (in the format "hashtag" or "#hashtag")
   
The REST service is built in Java, using:
* Spring Social framework to handle redirect to the Facebook login dialog, and the resulting redirect
* JAX-RS framework to provide REST end-points for User and Post resources
* The restfb framework to allow querying of the Facebook Graph API for user profile & post data

The web client is built using AngularJS

### Pre-requisites 

You will need the Java JDK (v 1.8) and Maven (v3.3.0 or greater) to build and run the application

## To build & start the service

```
# Start the service
$ mvn jetty:run
```
This will start a local web server instance, running on ``localhost:8080``

### Unit Tests

There are unit tests to verify the REST service functionality, located in ``src\main\test``

Run the tests as follows:

```
$ mvn test
```

### Caveats & Assumptions

I was unable to get the posts for the friends of the authenticated Facebook user. Far as I can tell (from the FB Graph API) this data is no longer available through the API -- so I have skipped that part of the assignment.

I also was not able to query the Graph API using a hashtag as a query value, so instead, I retrieved all posts & iterated through the list to get the matching posts. 

I believe that the Facebook App I created through the FB dashboard is a test app, so I need to add facebook users as testers through the dashboard in order for them to be able to use this test application. Please contact me if you dont seem to be able to successfully use the application with your Facebook ID. _(I'm not too familiar with this Facebook app configuration.)_ 





