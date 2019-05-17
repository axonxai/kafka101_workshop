# LAB-01 Maak een Kafka "hello world" producer en een consumer

Stappenplan
-   kafka-console-producer om berichten te sturen.
-   kafka-console-consumer om berichten uit te lezen.
-   Hello-world producer 
-   Hello-world consumer 
-   Key toevoegen aan je producer
-   Consumer aanpassen om key + message uit te lezen van je topic

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
ajf;lasjfl




## Een producer maken

