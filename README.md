# api-movies-example

A Rest API with basic functionalities

## Table of contents

- [How to run](#how-to-run)
- [How to test](#how-to-test)
- [How to clean](#how-to-clean)
- [Architecture](doc/architecture.md)
- [App Urls](doc/urls.md)

---

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

## How to clean

_Will clean related dockers containers, networks and disks_

```sh
make clean
```

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

[back to top](#api-movies-example)
