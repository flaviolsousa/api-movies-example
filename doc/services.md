# api-movies-example

## Services

[back to Architecture](architecture.md)

---

Service components are components capable of executing the entire business rule without the corruption of the presentation layer.

---

### Example: check others documents with same name 

One of highlights of this project is the rule where no movie should be inserted with the same name as a previous created movie.

For greater accuracy, regex was used, replacing spaces with multi-space regex expression.

When using regex we could lose performance when we stop using indexes, but this problem was avoided using the below strategy:

[Documentation MongoDB > Session "Index Use"](https://docs.mongodb.com/manual/reference/operator/query/regex/)

---

[back to top](#api-movies-example)
