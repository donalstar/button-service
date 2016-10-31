## Button Service

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

### Example Use Cases



### Sample Service Requests - Documentation

####  Create a task.

``POST /task``

_Request Example:_

```

$curl -H "Content-Type: application/json" -X POST \
-d '{"name":"my task","attributes": "{ \"ghg\": \"val\", \"p2\", \"val2\" }" }' http://[hostname]/task


```

_Response Example:_

```
HTTP/1.1 201 Created
Content-Type:application:json
{
“code”: 0,
“Message”: “Task created successfully”,
“Task_id”: 1
} 
```

####  Assign a task to a user.

``POST /task/{task-id}/assign/{user-id}``

_Request Example:_

```
$curl [hostname]/task/{task-id}/assign/{user-id}
```

_Response Example:_

```
HTTP/1.1 200 OK
Content-Type:application:json
{
“code”: 0,
“Message”: “Task assigned successfully”,
} 
```

####  Set a task status

``POST /task/{task-id}/setstatus/{status}``

_Request Example:_

```
$curl [hostname]/task/{task-id}/setstatus/complete
```

_Response Example:_

```
HTTP/1.1 200 OK
Content-Type:application:json
{
“code”: 0,
“Message”: “Task status updated successfully”,
} 
```

####  Fetch all tasks assigned to a user

``GET /task?user_id={user-id}``

_Request Example:_

```
$curl [hostname]/task?user_id={user-id}
```

_Response Example:_

```
HTTP/1.1 200 OK
Content-Type:application:json
{
“code”: 0,
“message”: “Success”,
“tasks”:  [
{ “id”: 1,
“Name”: “My Task”,
“Status” “Active”,
“Assigned”: 2,
“Attributes”: {
“param1” : “val1”,
etc.
},
{
etc.
}
]
} 
```


## Scripts to Run Some Simple Tests
```
$ cd bin

# Get all tasks assigned to a user
./get_user_tasks.sh

```





