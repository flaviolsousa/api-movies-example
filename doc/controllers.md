# api-movies-example

## Controllers

[back to Architecture](architecture.md)

---

Due to the large amount of configuration annotations in the controllers, I like to separate them in an interface even if the project does not have the need for an interface in the controllers.

---

#### List Movies

Request: GET > /movies?censorship=CENSORED|UNCENSORED

Retrieve a list of Movies.

The `censorship` query parameter is `optional`

---

#### Create Movie

`id` should not be informed

Request: POST > /movies

Body:

```json
{
  "name": "Era uma Vez em... Hollywood",
  "director": {
    "name": "Quentin Tarantino"
  },
  "cast": [
    {
      "name": "Margot Robbie"
    }
  ],
  "censorship": "CENSORED",
  "released": "2019-08-15"
}
```

---

#### Update a Movies

`id` must be informed and previously created

Request: PUT > /movies/{id}

Body:

```json
{
  "name": "Era uma Vez em... Hollywood",
  "director": {
    "name": "Quentin Tarantino"
  },
  "cast": [
    {
      "name": "Margot Robbie"
    }
  ],
  "censorship": "CENSORED",
  "released": "2019-08-15"
}
```

---

[back to top](#api-movies-example)
