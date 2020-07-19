# api-movies-example

## Exceptions

[back to Architecture](architecture.md)

---

### Model

All exceptions thrown by `controllers` have been normalized to `error/ErrorResponse.java` model

Example:

```json
{
  "timestamp": "2020-07-19T01:23:46.139602",
  "error": "400 BAD_REQUEST",
  "message": "Argument validation failed",
  "violations": [
    {
      "field": "director",
      "message": "must not be null"
    },
    {
      "field": "censorship",
      "message": "must not be null"
    },
    {
      "field": "director.name",
      "message": "must match \"^[\\p{L} .'-]{2,30}$\""
    }
  ]
}
```

---

[back to top](#api-movies-example)
