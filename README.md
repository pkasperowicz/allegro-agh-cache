# allegro-agh-cache

## Wymagania do uruchomienia aplikacji
* git
* docker
* java 17
* jmeter

## Quickstart

```shell
git clone git@github.com:pkasperowicz/allegro-agh-cache.git
cd allegro-agh-cache
./gradlew build
docker compose up --build
```

## Urls
* Aplikacja http://localhost:8080/cart/special-offer/{userId}
* [Prometheus](http://localhost:9090/) 
* [Graphana](http://localhost:3000/) admin/admin
