# sim_test_project

## Project Requirements:

Your goal will be to create a skeleton of a resilient user catalog service and test client. This service provides a REST interface that allows to PUT and GET users. A user consists of a username, a first name and a last name.

> Your goal will be to create a skeleton of a resilient user catalog service and test client. This service provides a REST interface that allows to PUT and GET users. A user consists of a username, a first name and a last name.

> Your task is to also write a command-line program that runs over a relatively long time (say, 5 minutes) doing some predefined set of operations (like, adding a number of users, then retrieving them, etc.).
> This client will receive multiple entry-points and should be able to balance the load among them, and also continue working if one (or more) of the supplied entry-point stops responding.
> You should actually program the load-balancing and fail-over logic and not use any library (using a HTTP library is of course OK and encouraged).
> For evaluation of your assignment, we will actually fail a server and check if the client still works. We could also put back the failed server, then fail another, etc. The program should work without failures if at least one server is up, and always distribute the load if there is more than one server available. Ideally, in the cases when not all servers are up, performance should not degrade significantly, compared to the case when all servers are running.

## Solution:

### Stack of Technologies:
Java 8, Maven, Spring Boot, Hibernate, MySQL, Docker, Swagger, Apache HTTP Cleint (For Client Logic)

### How to Start:

#### Services infrastructure using Docker
In project folder just run the: 
```
docker-compose up
```
this command will build the Service application and start it in 3 docker containers that work with MySQL in other docker container
Services will be started and will be available on 8091, 8092, 8093 ports.

For simulate host availability, it easy to use 'pause', 'unpauuse' docker command. 
At the begining you should find the container name that you want to stop using:
```
docker ps
```
then take the needed container name and pause it 
```
docker pause <container_ID>
```
or unpause it 
```
docker unpause <container_ID>
```

In case if you want to run the new additional Service instance, you must do this when your docker-compose is UP,
because you need MySQL :)
We need find the docker-compose nework using:
```
docker network ls
```
Generaly in should have name : simtestproject_default
When you will check the network name, you could starn new app using such command, it will start container on 8094 port:
```
docker run -it -p 8094:8080 --link testproject_uc_mysql_1:uc_mysql --net testproject_default user_catalog_boot /bin/bash /usr/src/app/docker/startup.sh
```
NOTE: you could also run docker container as Daemon using '-d' attribute.

#### Start the Client
You should build the client app using Maven:
```
maven clean package
```
then you just need start the client with list of hosts(and ports if needed):
```
java -jar ./user-catalog-client/target/userCatalogTestRunner.jar "localhost:8091" "localhost:8092" "localhost:8093" "localhost:8094"
```
this line will start client and will try work with 4 hosts
