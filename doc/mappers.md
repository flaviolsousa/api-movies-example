# api-movies-example

## Mappers

[back to Architecture](architecture.md)

---

To avoid exposing the application model, the DTO strategy was adopted.

[MapStruct](https://mapstruct.org/) was used to generate mappers between `DTOs` and `Models`. It's a Java Bean mapper.

This `AnnotationProcessor` automatically generates mappers class that converts two Java Beans.

[Documentation](https://mapstruct.org/documentation/dev/reference/html/)

---

[back to top](#api-movies-example)
