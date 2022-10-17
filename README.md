# How to use
+ With Docker and docker-compose:

```sh
$ git clone https://github.com/collins169/urlshortenerapi.git
$ cd UrlShortener-API 
$ docker-compose up 
```

    - Open localhost:8080/swagger-ui.html to see endpoints. 

- Without Docker:
```sh
$ git clone https://github.com/collins169/urlshortenerapi.git
```
    - Make sure you have access to local or any Postgres Database.
    - Open project in your favorite editor and change application.properties file to point to your Postgres database
    - Build Spring project 
    - Open localhost:8080/swagger-ui.html to see endpoints.