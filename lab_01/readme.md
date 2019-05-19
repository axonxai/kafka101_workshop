# LAB-01 Maak een Kafka "hello world" producer en een consumer

## Berichten sturen met de CLI
### Een topic creëren
```kafka-topics --zookeeper <ip-adres>:2181 --create --replication-factor 1 --partitions 1 --topic hello_world```

Als het goed is zie je nu je aangemaakte topic:
```kafka-topics --zookeeper <ip-adres>:2181 --list```

### Een bericht sturen
```kafka-console-producer --broker-list <ip-adres>:9092 --topic hello_world```
Nu kun je een reeks berichten sturen naar dit topic

### Een bericht bekijken
```kafka-console-consumer --bootstrap-server <ip-adres>:9092 --topic hello_world```
Nu kun je het topic uitlezen en komen hier nieuwe berichten voorbij. 
Als je alle berichten wilt zien voeg je `--from-beginning` toe aan het commando. 


## Een producer maken
Open je editor en voeg je ip adres en de juiste port toe toe in de java code,

Daarna kun je alles compileren met:
```sh
$ mvn clean install
```
Nu starten we het java programma, en run de 'fat' jar met:

```sh
$ java -jar ./target/ai.axonx.workshop-hello-world-producer-1.0-SNAPSHOT.jar
```

## Een producer maken
Open je editor en voeg je ip adres en de juiste port toe toe in de java code,

Daarna kun je alles compileren met:
```sh
$ mvn clean install
```
Nu starten we het java programma, en run de 'fat' jar met:

```sh
$ java -jar ./target/ai.axonx.workshop-hello-world-consumer-1.0-SNAPSHOT.jar
```
