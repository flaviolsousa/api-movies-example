# api-movies-example

## Tests

[back to Architecture](architecture.md)

---

### Coverage:

[JaCoCo](https://www.eclemma.org/jacoco/) was used to collect coverage data during the execution of the tests

![Image of Architecture](images/coverage.png)

---

### Templates:

**Packages:**

- test/java/br.com.fs.api.movies.**templates**

To generate random objects the framework was used: [java-faker](https://github.com/DiUS/java-faker)

**About `java-faker`**

- [Home](https://github.com/DiUS/java-faker)
- [JavaDoc](http://dius.github.io/java-faker/apidocs/index.html)
- [GitHub](https://github.com/DiUS/java-faker)
- [Example](https://java-faker.herokuapp.com/)
- [Tutorial](https://www.baeldung.com/java-faker)

---

### Unit Tests

**Packages:**

- test/java/br.com.fs.api.movies.**controller**
- test/java/br.com.fs.api.movies.**mapper**
- test/java/br.com.fs.api.movies.**service**

Each Component is fully tested individually

---

### Integration Tests

**Packages:**

- test/java/br.com.fs.api.movies.**integration**

As there was only one entity exposed with restful (Movie) I made only one class of integrated test ([MovieTest.java](https://github.com/flaviolsousa/api-movies-example/blob/develop/src/test/java/br/com/fs/api/movies/integration/MovieTest.java)).

MovieTest.java test [all components](https://github.com/flaviolsousa/api-movies-example/blob/develop/doc/images/architecture.png) involved with Movie entity as they were designed, without any changes, including the repository with the help of a [Embedded MongoDB](https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo).

---

### Functional Tests

_No need_

---

### The helper TestUtil.java

This component was built specifically to assist in the development of tests for all layers of this project.

[Source](https://github.com/flaviolsousa/api-movies-example/blob/develop/src/test/java/br/com/fs/api/movies/TestUtil.java)

Methods:

- List<T> gimme(int amount, Supplier<T> templateFunction)
  _Created to generate `n` instances of a specific template._

- LocalDate toLocalDate(Date dateToConvert)

- String **toJson**(Object objectToJson)

- <T> T **toObject**(String json, Class<T> clazz)

---

[back to top](#api-movies-example)
