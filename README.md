# api-movies-example

A Rest API with basic functionalities

## Table of contents

- [Requirements](#requirements)
- [How to run](#how-to-run)
- [How to test](#how-to-test)
- [How to clean](#how-to-clean)
- [Architecture](doc/architecture.md)
- [App Urls](doc/urls.md)

---

## Requirements:

- JRE 11
- Apache Maven

## How to run

### Start everything in containers:

```sh
make build run
```

### Start application on IDE and dependencies on containers:

```sh
make run-dependencies
```

**Run:** br.com.fs.api.movies.ApiMoviesExampleApplication.java

> **Access url:** [http://localhost:8080/](http://localhost:8080/)

---

## How to test

_Before proceeding, stop all related containers_

```sh
make test
```

or

```sh
mvn clean test
```

---

## How to clean

_Will clean related dockers containers, networks and disks_

```sh
make clean
```

## About the author

I'm Flávio Lopes de Sousa, I'm working as a Technical Leader at CVC Corp and I'm currently responsable for the development and maintenance of the gateway of car product , using Java, NodeJs, K8s and AWS Cloud.

---

[back to top](#api-movies-example)
