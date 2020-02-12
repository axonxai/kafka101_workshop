# LAB-7 Chaos monkey spelen in een Kafka cluster

We gaan een kijkje nemen naar hoe robuust een Kafka cluster is.
Hier gebruiken we een andere docker-compose.yml, dus sluit eerst de vorige docker containers af door `docker-compose down` te gebruiken in de map cp-all-in-one.

Start nu het Kafka cluster met 3 Zookeepers en 3 Kafka brokers op met 

    docker-compose up -d --build

Mocht er alsnog een foutmelding komen kan je _alle docker containers_ even afsluiten met `docker kill $(docker ps -q)` en het opnieuw proberen.

Als je `docker ps` tikt krijg je als het goed is 3 Zookeepers en 3 Kafka brokers te zien

    CONTAINER ID        IMAGE                              COMMAND                  CREATED             STATUS              PORTS               NAMES
    81efe19b8f4b        confluentinc/cp-kafka:latest       "/etc/confluent/dock…"   5 minutes ago       Up 1 second                             lab04_kafka-2_1
    55299d2a5fec        confluentinc/cp-kafka:latest       "/etc/confluent/dock…"   5 minutes ago       Up 1 second                             lab04_kafka-1_1
    d991d3c1dd72        confluentinc/cp-kafka:latest       "/etc/confluent/dock…"   5 minutes ago       Up 1 second                             lab04_kafka-3_1
    2d2749d0dfad        confluentinc/cp-zookeeper:latest   "/etc/confluent/dock…"   5 minutes ago       Up 1 second                             lab04_zookeeper-3_1
    18f883e0da9e        confluentinc/cp-zookeeper:latest   "/etc/confluent/dock…"   5 minutes ago       Up 1 second                             lab04_zookeeper-2_1
    165d74cf29b0        confluentinc/cp-zookeeper:latest   "/etc/confluent/dock…"   5 minutes ago       Up 1 second                             lab04_zookeeper-1_1


Met Kafka gaan we allereerst een nieuw topic aanmaken met een replication factor van 3.

    kafka-topics --zookeeper localhost:22181,localhost:32181,localhost:42181 --create --replication-factor 3 --partitions 1 --topic robuust

Vervolgens kunnen we de Kafka brokers berichtjes sturen, ofwel met kafka console tools ofwel door in de java code even het topic en de servers aan te passen.

    kafka-console-producer --broker-list localhost:19092,localhost:29092,localhost:39092 --topic robuust

Idem voor de consumer

    kafka-console-consumer --bootstrap-server localhost:19092,localhost:29092,localhost:39092 --topic robuust

## Chaos monkey

Terwijl producers en consumers in de achtergrond draaien, kan je kijken hoe ze het doen terwijl je docker instanties sloopt met

    docker stop <container id>

De container ids kan je opzoeken met `docker ps`.
