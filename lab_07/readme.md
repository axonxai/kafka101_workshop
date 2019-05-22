# LAB-07 Kafka connect - inlezen SQL lite data [UNDER CONSTRUCTION]

-   Nog uitwerken
-   Maak gebruik van Kafka connect en zoek de kafka-connect jdbc plugin
-   Vind je settings voor deze connector in de etc map en pas deze aan zodat verwezen wordt naar de juiste database "./data/sql-lite-db-link"
-   Laat de connector draaien om een scheldwoorden én Amerika synoniemen tabel in te laden
-   bekijk of je de nieuwe topics en data ziet m.b.v. Kafka-console-consumer 
-   Maakt connect hier meteen ktables van ? of moet je die handmatig maken? Dit nog verder aanvullen dus.
-   

Met dit lab willen we laten zien, dat het mogelijk is om zowel uit database te lezen uit en te schrijven naar Kafka. Ook kun je up to date blijven bij Database wijzigingen. Er zijn heel veel mogelijkheden qua connectoren, bekijk [https://www.confluent.io/hub]. Nu gaan we aan de slag om een SQLite database uit te lezen via Kafka-Connect. Hiervoor gaan we gebruik maken van de JDBC connector van Kafka-Connect.

Eén image zijn we vergeten te vermelden in de voorbereiding
```sh
$ docker pull confluentinc/cp-kafka-connect:5.2.1
```

Het is nodig om nog wat aanpassingen te doen in de properties file van de jdbc connector
```sh
# Pas de properties file van de plugin aan op twee punten:
$ vi <confluent-directory>/etc/kafka-connect-jdbc/source-quickstart-sqlite.properties
$ connection.url=jdbc:sqlite:<directory/to/the/database/>kafka-workshop-lab_07.db
$ topic.prefix=kafka_workshop_lab_07_sqlite_jdbc_

# Pas de docker-compose file aan om een je properties file, database en je plugin locatie te mounten:
# voor de service "connect"
$ volumes:
    - ./jars:/etc/kafka-connect/jars/
    - ../lab_07/confluent/etc/kafka-connect-jdbc:/etc/kafka-connect-jdbc/
    - ../lab_07/db:/data/
```

Nu gaan we Kafka-connect opstarten met de juiste configuraties en SQLite installeren in de docker container
```sh
# $ docker run confluentinc/cp-kafka-connect:latest -d 
$ docker-compose up -d
$ docker exec -it connect bash

# install SQLite on the docker container
$ apt-get update
$ apt-get install sqlite3
```

Bekijk welke plugins actief zijn [http://localhost:8083/connector-plugins]



Testen of er data binnenkomt
```sh
$ kafka-avro-console-consumer \
--bootstrap-server localhost:9092 \
--property schema.registry.url=http://localhost:8081 \
--property print.key=true \
--from-beginning \
--topic kafka_workshop_lab_07_sqlite_jdbc_usa_synoniemen
```


Voeg meer data toe in de SQLite tabel, dit komt nu direct binnen op het topic in Kafka.




### Aantekeningen

```sh
# create the connector
$ curl -X POST \
  -H "Content-Type: application/json" \
  --data '{ "name": "quickstart-jdbc-source", "config": { "connector.class": "io.confluent.connect.jdbc.JdbcSourceConnector", "tasks.max": 1, "connection.url": "jdbc:sqlite:<directory/to/the/database/>kafka-workshop-lab_07.db", "mode": "incrementing", "incrementing.column.name": "id", "timestamp.column.name": "modified", "topic.prefix": "kafka_workshop_lab_07_sqlite_jdbc_", "poll.interval.ms": 1000 } }' \
  http://localhost:8083/connectors

````
