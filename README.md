# ADMIOS Flix Movies Management REST API

To easily test the movies-api, you can use next Heroku URL: https://admiosflix-movies-management.herokuapp.com/

Please have in mind that POST/PUT/DELETE methods require authentication. 

Just for testing purposes you can find the admin user/pwd in application.properties file.

Because the app has a Docker file you can run it with next commands

## Install

    docker-compose build

## Run the app

    docker-compose up

If you don't have docker installed, you can run it with maven, but keep in mind that you need to create a postgresql database and update connection values
in application.properties to make it work.

# REST API

The REST API is described below.

## Get movie by movie-id

### Request

`GET /movies-management-api/v1/movies/{movie-id}`

    curl -X GET "https://admiosflix-movies-management.herokuapp.com/movies-management-api/v1/movies/{movie-id}" -H  "accept: */*"
    
    request url https://admiosflix-movies-management.herokuapp.com/movies-management-api/v1/movies/1

### Response

    HTTP/1.1 200 OK
    Date: Tue,27 Apr 2021 15:21:18 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json

    Body
    {
        "title": "Rocky",
        "crew": "Directed By: John Avildsen, Produced By: Irwin Winkler",
        "cast": "Sylvester Stallone as Robert Balboa",
        "rating": 9.3,
        "releaseDate": "1970-01-13T00:00:00.000+00:00",
        "genre": {
        "id": 1,
        "description": "Action"
        },
        "id": 1
    }

## Get movies

### Request 

`GET /movies-management-api/v1/movies/`

    curl -X GET "https://admiosflix-movies-management.herokuapp.com/movies-management-api/v1/movies/" -H  "accept: */*"
    
    request url https://admiosflix-movies-management.herokuapp.com/movies-management-api/v1/movies

### Response

    HTTP/1.1 200 OK
    Date: Tue,27 Apr 2021 15:21:18 GMT 
    Status: 200 OK
    Connection: close
    Content-Type: application/json

    Body
    {
    "content": [
        {
            "title": "Indiana Jones and the Last Crusade",
            "crew": "Directed By: Steven Spielberg, Produced By: Robert Watts, Story By: George Lucas",
            "cast": "Harrison Ford as Indiana Jones",
            "rating": 8.5,
            "releaseDate": "1989-05-24T00:00:00.000+00:00",
            "genre": {
                "id": 2,
                "description": "Adventure"
            },
            "id": 3
        },
        {
            "title": "Rocky",
            "crew": "Directed By: John Avildsen, Produced By: Irwin Winkler",
            "cast": "Sylvester Stallone as Robert Balboa",
            "rating": 9.3,
            "releaseDate": "1970-01-13T00:00:00.000+00:00",
            "genre": {
                "id": 1,
                "description": "Action"
            },
            "id": 1
        },
        {
            "title": "The Shawshank Redemption",
            "crew": "Directed By: Frank Darabont, Produced By: Niki Marvin, Story By: Rita Hayworth and Shawshank Redemption by Stephen King",
            "cast": "Tim Robbins as Andy Dufresne, Morgan Freeman as Ellis Boyd Redding",
            "rating": 9.8,
            "releaseDate": "1995-03-25T00:00:00.000+00:00",
            "genre": {
                "id": 9,
                "description": "Drama"
            },
            "id": 2
            }
        ],
        "pageable": {
            "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
            "pageNumber": 0,
            "pageSize": 10,
            "offset": 0,
            "unpaged": false,
            "paged": true
        },
            "totalPages": 1,
            "totalElements": 3,
            "last": true,
            "first": true,
            "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
            "numberOfElements": 3,
            "size": 10,
            "number": 0,
            "empty": false
        }
    }

## Get movies by Genre

### Request

`GET /movies-management-api/v1/movies/genre/{genre-description}`

    curl -X GET "https://admiosflix-movies-management.herokuapp.com/movies-management-api/v1/movies/genre/action" -H  "accept: */*"
    
    request url https://admiosflix-movies-management.herokuapp.com/movies-management-api/v1/movies/genre/action

### Response

    HTTP/1.1 200 OK
    Date: Tue,27 Apr 2021 15:21:18 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json

    Body
    {
    "content": [
        {
            "title": "Rocky",
            "crew": "Directed By: John Avildsen, Produced By: Irwin Winkler",
            "cast": "Sylvester Stallone as Robert Balboa",
            "rating": 9.3,
            "releaseDate": "1970-01-13T00:00:00.000+00:00",
            "genre": {
                "id": 1,
                "description": "Action"
            },
            "id": 1
        }
    ],
    "pageable": {
        "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
        "pageNumber": 0,
        "pageSize": 10,
        "offset": 0,
        "unpaged": false,
        "paged": true
    },
        "totalPages": 1,
        "totalElements": 1,
        "last": true,
        "first": true,
        "sort": {
        "sorted": true,
        "unsorted": false,
        "empty": false
    },
        "numberOfElements": 1,
        "size": 10,
        "number": 0,
        "empty": false
    }

## Get genres

### Request

`GET /movies-management-api/v1/genres/" -H  "accept: */*"`

    curl -X GET "https://admiosflix-movies-management.herokuapp.com/movies-management-api/v1/genres" -H  "accept: */*"
    
    request url https://admiosflix-movies-management.herokuapp.com/movies-management-api/v1/genres

### Response

    HTTP/1.1 200 OK
    Date: Tue,27 Apr 2021 15:21:18 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json

    Body
    [
      {
        "id": 1,
        "description": "Action"
      },
      {
        "id": 2,
        "description": "Adventure"
      },
      {
        "id": 3,
        "description": "Comedy"
      },
      {
        "id": 4,
        "description": "Crime"
      },
      {
        "id": 5,
        "description": "Mystery"
      },
      {
        "id": 6,
        "description": "Fantasy"
      },
      {
        "id": 7,
        "description": "Historical"
      },
      {
        "id": 8,
        "description": "Horror"
      },
      {
        "id": 9,
        "description": "Drama"
      },
      {
        "id": 10,
        "description": "Science Fiction"
      },
      {
        "id": 11,
        "description": "Terror"
      },
      {
        "id": 12,
        "description": "Thriller"
      },
      {
        "id": 13,
        "description": "Western"
      },
      {
        "id": 14,
        "description": "Other"
      }
    ]
    
## Authenticate

POST, PUT and DELETE methods are authenticated. 

### Request

`POST /movies-management-api/v1/authenticate`

    curl -X POST "https://admiosflix-movies-management.herokuapp.com/movies-management-api/v1/authenticate" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"string\",\"password\":\"string\"}"

    request url https://admiosflix-movies-management.herokuapp.com/movies-management-api/v1/authenticate

### Response

    HTTP/1.1 200 Created
    Date: Tue,27 Apr 2021 15:21:18 GMT
    Status: 200
    Connection: close
    Content-Type: application/json

    Body
    {
        "jwttoken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pb3NmbGl4YWRtaW4iLCJleHAiOjE2MTk1NjI5OTIsImlhdCI6MTYxOTU0NDk5Mn0.9x7lncGMuwgrtcLjt-V5EEtN1NfyV1lOOxTc0S-ZTjsw6WOFm0-0E7_AezJ19NvDfdLKksHXrMIzQ0ghgKjz_Q"
    }

## Create a new movie

### Request

`POST /movies-management-api/v1/movies`

    curl -X POST "https://admiosflix-movies-management.herokuapp.com/movies-management-api/v1/movies" -H  "accept: */*" -H  "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pb3NmbGl4YWRtaW4iLCJleHAiOjE2MTk1NjI5OTIsImlhdCI6MTYxOTU0NDk5Mn0.9x7lncGMuwgrtcLjt-V5EEtN1NfyV1lOOxTc0S-ZTjsw6WOFm0-0E7_AezJ19NvDfdLKksHXrMIzQ0ghgKjz_Q" -H  "Content-Type: application/json" -d "{\"title\":\"string\",\"crew\":\"string\",\"cast\":\"string\",\"rating\":0,\"releaseDate\":\"2021-04-27T17:33:35.219Z\",\"genre\":{\"id\":0,\"description\":\"string\"}}"

    request url https://admiosflix-movies-management.herokuapp.com/movies-management-api/v1/movies

### Response

    HTTP/1.1 200
    Date: Tue,27 Apr 2021 15:21:18 GMT
    Status: 201
    Connection: close
    Content-Type: application/json

    Body
    { "objectId": 4, "message": "Movie successfully created" }

## Update a movie

### Request

`PUT /movies-management-api/v1/movies/{movie-id}`

    curl -X PUT "https://admiosflix-movies-management.herokuapp.com/movies-management-api/v1/movies/1" -H  "accept: */*" -H  "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pb3NmbGl4YWRtaW4iLCJleHAiOjE2MTk1NjI5OTIsImlhdCI6MTYxOTU0NDk5Mn0.9x7lncGMuwgrtcLjt-V5EEtN1NfyV1lOOxTc0S-ZTjsw6WOFm0-0E7_AezJ19NvDfdLKksHXrMIzQ0ghgKjz_Q" -H  "Content-Type: application/json" -d "{\"title\":\"Rocky\",\"crew\":\"Directed By: John Avildsen, Produced By: Irwin Winkler\",\"cast\":\"Sylvester Stallone as Robert Balboa\",\"rating\":9.3,\"releaseDate\":\"1970-01-13T00:00:00.000+00:00\",\"genre\":{\"id\":1,\"description\":\"Action\"},\"id\":1}"

    request url https://admiosflix-movies-management.herokuapp.com/movies-management-api/v1/movies/1

### Response

    HTTP/1.1 200
    Date: Tue,27 Apr 2021 15:21:18 GMT
    Status: 200
    Connection: close
    Content-Type: application/json

    Body
    { "objectId": 1, "message": "Movie successfully updated" }

## Delete a movie

### Request

`DELETE /movies-management-api/v1/movies/{movie-id}`

    curl -X DELETE "https://admiosflix-movies-management.herokuapp.com/movies-management-api/v1/movies/1" -H  "accept: */*" -H  "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pb3NmbGl4YWRtaW4iLCJleHAiOjE2MTk1NjI5OTIsImlhdCI6MTYxOTU0NDk5Mn0.9x7lncGMuwgrtcLjt-V5EEtN1NfyV1lOOxTc0S-ZTjsw6WOFm0-0E7_AezJ19NvDfdLKksHXrMIzQ0ghgKjz_Q"

    request url https://admiosflix-movies-management.herokuapp.com/movies-management-api/v1/movies/1

### Response

    HTTP/1.1 200
    Date: Tue,27 Apr 2021 15:21:18 GMT
    Status: 200
    Connection: close
    Content-Type: application/json

    Body
    { "objectId": null, "message": "Movie successfully deleted" }

# Technologies

- Spring Boot
- Maven
- Docker
- Postgresql
- JWT

API includes authentication. User and password are included in application.properties