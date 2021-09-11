#Garip Tipici 

Development Started: 28 July

Total time spent on development: 3.5 - 4 Hours

# Pet Fish Co. REST Service


A minimal [Spring Boot](http://projects.spring.io/spring-boot/) Web app.

## Requirements

For building and running the application you need:

- [Docker](https://www.docker.com/products/docker-desktop)

## Running the application locally

There are several ways to run Petfish Docker container on your local machine. One way is to execute the docker compose command:

```shell
docker-compose up  
```
App will start to listen the local port `8081`, so please kill the processes that uses this port  before running the application.

You can use any tool to send requests to the service, I recommend [PostMan](https://www.postman.com).

## API Endpoints and samples

To create a new aquarium you need to `POST` an aquarium data. Please use GALLON or LITER as measurement unit. 

```shell
http://localhost:8081/aquariums
```

```json
{
  "glassType": "sapphire",
  "shape": "sphere",
  "size": 444.8,
  "unit": "LITER"
}
```

To read the aquariums you created, you should `GET`to same endpoint:

```shell
http://localhost:8081/aquariums
```

You can `GET` a specific aquarium by id in path:

```shell
http://localhost:8081/aquariums/1
```

To create a new fish, you need an aquarium, so you need an aquarium id to create a fish. 
You should `POST` the fish to:

```shell
http://localhost:8081/aquariums/1/fishes
```
You can create only HAMSI, GUPPY and GOLDFISH for now.
```json
{
  "species": "HAMSI",
  "color": "white",
  "numberOfFins": 3
}
```

You can update the fish you created even you can `PUT` it in a new aquarium by specifying a new aquarium id in object.
```shell
http://localhost:8081/aquariums/1/fishes/2
```
```json
{
  "aquariumId": 9,
  "species": "HAMSI",
  "color": "gray",
  "numberOfFins": 4
}
```

You can `GET` all of the fishes in a specific aquarium

```shell
http://localhost:8081/aquariums/1/fishes
```

or you can `GET` a specific fish in a specific aquarium.

```shell
http://localhost:8081/aquariums/1/fishes/2
```

You also can use this service in USA. By adding `?locale=US` to all queries, you can see USA measurement units in responses:

```shell
http://localhost:8081/aquariums?locale=US
```
