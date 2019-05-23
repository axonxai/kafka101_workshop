# LAB-07 Kafka connect - inlezen SQL lite data 

Met dit lab willen we laten zien, dat het mogelijk is om zowel uit database te lezen uit en te schrijven naar Kafka. Ook kun je up to date blijven bij Database wijzigingen. Er zijn heel veel mogelijkheden qua connectoren, bekijk [https://www.confluent.io/hub]. Nu gaan we aan de slag om een SQLite database uit te lezen via Kafka-Connect. Hiervoor gaan we gebruik maken van de JDBC connector van Kafka-Connect.


## Docker-compose aanpassen voor Kafka-Connect
Eén image zijn we vergeten te vermelden in de voorbereiding
```sh
$ docker pull confluentinc/cp-kafka-connect:5.2.1
```

Het is nodig om nog wat aanpassingen te doen in de properties file van de jdbc connector 
(Let op: dit is inmiddels al voor je aangepast en hoeft niet meer)
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
$ docker-compose up -d
```

## Kennismaken met Kafka-Connect

Bekijk welke plugins actief zijn [http://localhost:8083/connector-plugins], zie je de jdbc connector erbij staan? Als je [http://localhost:8083/connectors] bezoekt, zie je dat er nog geen connectors actief zijn. 

Start KSQL op en kijk welke topics er aanwezig zijn. Als het goed is, zie je geen topic met de naam "kafka_workshop_lab_07_sqlite_jdbc_usa_synoniemen", deze gaan we nu automatisch aan laten maken en vullen via Kafka-Connect.

Upload nieuwe connector configuratie om zo de database in Kafka in de laden via Kafka-connect (zie folder "./lab_07/db/").
```sh
$ curl -X POST \
  -H "Content-Type: application/json" \
  --data '{ "name": "quickstart-jdbc-source", "config": { "connector.class": "io.confluent.connect.jdbc.JdbcSourceConnector", "tasks.max": 1, "connection.url": "jdbc:sqlite:/data/kafka-workshop-lab_07.db", "mode": "incrementing", "incrementing.column.name": "id", "timestamp.column.name": "modified", "topic.prefix": "kafka_workshop_lab_07_sqlite_jdbc_", "poll.interval.ms": 1000 } }' \
  http://localhost:8083/connectors 
```

Je net toegevoegde connector is nu te zien via [http://localhost:8083/connectors]

De connector gaat nu meteen aan de slag, test nu of er data is ingeladen in het nieuwe topic via ksql of onderstaande commando. 
```sh
$ kafka-avro-console-consumer \
--bootstrap-server localhost:9092 \
--property schema.registry.url=http://localhost:8081 \
--property print.key=true \
--from-beginning \
--topic kafka_workshop_lab_07_sqlite_jdbc_usa_synoniemen
```

Voeg meer data toe in de SQLite tabel, als het goed is zie je dit direct bijgewerkt worden op het topic in Kafka.
```sh
$ docker exec -it connect bash

# install SQLite on the docker container of je eigen laptop (naar eigen keuze)
$ apt-get update
$ apt-get install sqlite3

# Voeg een item toe
$ sqlite3 /data/kafka-workshop-lab_07.db
$ INSERT INTO usa_synoniemen(word) VALUES('Noord-Amerika');
```

Er is iets in de SQLite database toegevoegd, Kafka-connect ziet de wijziging en in je consumer of in KSQL zie je dit nieuwe item nu verschijnen. 

