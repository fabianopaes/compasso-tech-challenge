# Compasso Tech Challenge

The compasso-tech-challenge repository contains all projects that you need to run the tech challenge solution properly. It's an API which deal with a CRUD of cities and customers.

This repo is organized as follow

* compasso-rest-api
* compasso-e2e-tests

## Prerequisites

You need the following things properly installed on your own machine.

* [Docker](https://github.com/Netflix/eureka)
* [Docker Compose](https://docs.docker.com/compose/install/)
* [Gradle](https://gradle.org/) 
* [Make](https://www.gnu.org/software/make/manual/make.html)

## Running Rest API

First thing that you need to do is to build the whole project, so just execute the command bellow in the project root directory

``` console
$ make build
```

This command wraps the gradle build

If you don't have gradle installed you still can choice to run using the gradle wrapper that comes with the project, so run the command

``` console
$ cd compasso-rest-api
$ ./gradlew build
```

So now you are ready to start up the application.


The follow command will download the [MongoDB](https://www.mongodb.com) image and start, besides that it will compile a docker image for compasso-rest-api and will start it up on http:localhost:8080.

```console
$ make run-local
`````


## Running tests


compasso-rest-api has three types of test: unit tests, integration test and acceptance tests, to run unit and integration tests you can perform:

``` console
$ make test
```

If you want to run the acceptence  tests you can run the follow command:

``` console
$ make test-acceptence 
```

When you decide to analyze the code you might think helpfull to check the coverage report, so you can generate it running the follow command:

``` console
$ make cover 
```
This commando wraps the jacoco command and it will generate the report which is going to be available at $PROJECT_PATH/compasso-rest-api/build/reports/jacoco/


## compasso-rest-api

The compasso-rest-api is the application that deals with a CRUD of cities and customers, it uses a [mongodb]() as database and after you run make run-local it will be accessible on http://localhost:8080.

Since the app is up you can perform some tests to validate the solution, just import the postman collection /compasso-requests-examples/postman and enjoy it ...

```console
$ cd $PROJECT_PATH//compasso-requests-examples/postman
`````

### compasso-rest-api Documentation

Publish HTML version ([docker](https://www.docker.com/community-edition) required):

```console
$ make publish-documentation
```

### Observations

I used gradle instead of maven cause I am a little bit more familiar with that but if the maven is a really requirement I can portable the project asap, just let me know.