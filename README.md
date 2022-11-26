# allegro-agh-cache

## Wymagania do uruchomienia aplikacji
* git
* docker
* java 17
* jmeter

## Quickstart

```shell
git clone git@github.com:pkasperowicz/allegro-agh-cache.git
./gradlew build
docker compose up --build
```

## JMeter tests

Download jmeter from [here](https://dlcdn.apache.org//jmeter/binaries/apache-jmeter-5.5.zip) 
Open performanceTests/app.jmx in jmeter and run it. 

## Urls
* Aplikacja http://localhost:8080/cart/special-offer/{userId}
* [Prometheus](http://localhost:9090/) 
* [Graphana](http://localhost:3000/) admin/admin
