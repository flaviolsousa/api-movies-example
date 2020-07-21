# api-movies-example

## Architecture

[back to main](../README.md)

---

## Table of contents

- [Controllers](controllers.md)
- [Services](services.md)
- [Mappers](mappers.md)
- [Validations](validations.md)
- [Exceptions](exceptions.md)
- [Tests](tests.md)
- [Configs](configs.md)

---

![Image of Architecture](images/architecture.png)

---

## Project Structure

```
.
├─ data
│   └─ git
│       └─ pre-commit
│
├─ doc
│   ├─ architecture.md
│   ├─ exceptions.md
│   ├─ images
│   │   ├─ architecture.drawio
│   │   ├─ architecture.png
│   │   └─ coverage.png
│   ├─ mappers.md
│   ├─ tests.md
│   ├─ urls.md
│   └─ validations.md
│
├─ docker
│   ├─ docker-compose.dependencies.yml
│   ├─ docker-compose.yml
│   └─ Dockerfile
│
├─ LICENSE
├─ Makefile
├─ pom.xml
├─ README.md
│
└─ src
    ├─ main
    │   ├─ java
    │   │   └─ br.com.fs.api.movies
    │   │        ├─ ApiMoviesExampleApplication.java
    │   │        ├─ config
    │   │        │   ├─ HooksConfig.java
    │   │        │   └─ SwaggerConfig.java
    │   │        ├─ controller
    │   │        │   ├─ impl
    │   │        │   │   └─ MovieControllerImpl.java
    │   │        │   ├─ MovieController.java
    │   │        │   └─ WebController.java
    │   │        ├─ exception
    │   │        │   ├─ ApiMovieException.java
    │   │        │   ├─ ApiMovieValidationException.java
    │   │        │   └─ RestExceptionHandler.java
    │   │        ├─ model
    │   │        │   ├─ Actor.java
    │   │        │   ├─ Censorship.java
    │   │        │   ├─ Director.java
    │   │        │   ├─ dto
    │   │        │   │   ├─ ActorDto.java
    │   │        │   │   ├─ DirectorDto.java
    │   │        │   │   ├─ filter
    │   │        │   │   │   └─ MovieFilterDto.java
    │   │        │   │   └─ MovieDto.java
    │   │        │   ├─ error
    │   │        │   │   ├─ ErrorResponse.java
    │   │        │   │   └─ Violation.java
    │   │        │   ├─ mapper
    │   │        │   │   ├─ ActorMapper.java
    │   │        │   │   ├─ DirectorMapper.java
    │   │        │   │   └─ MovieMapper.java
    │   │        │   └─ Movie.java
    │   │        ├─ repository
    │   │        │   └─ MovieRepository.java
    │   │        └─ service
    │   │            └─ MovieService.java
    │   └─ resources
    │       ├─ application.yml
    │       └─ banner.txt
    │
    │
    └─ test
        └─ java
            └─ br.com.fs.api.movies
                 ├─ controller
                 │   └─ MovieControllerTest.java
                 ├─ integration
                 │   └─ MovieTest.java
                 ├─ mapper
                 │   ├─ ActorMapperTest.java
                 │   ├─ DirectorMapperTest.java
                 │   ├─ filters
                 │   └─ MovieMapperTest.java
                 ├─ service
                 │   └─ MovieServiceTest.java
                 ├─ templates
                 │   ├─ BaseTemplate.java
                 │   ├─ dto
                 │   │   ├─ ActorDtoTemplate.java
                 │   │   ├─ DirectorDtoTemplate.java
                 │   │   ├─ filters
                 │   │   │   └─ MovieFilterDtoTemplate.java
                 │   │   └─ MovieDtoTemplate.java
                 │   └─ model
                 │       ├─ ActorTemplate.java
                 │       ├─ DirectorTemplate.java
                 │       └─ MovieTemplate.java
                 └─ TestUtil.java
```

---

[back to top](#api-movies-example)
