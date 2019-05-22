[Next Lab](https://github.com/axonxai/kafka101_workshop/tree/iteratie_01/lab_02)

# LAB-01 Maak een Kafka "hello world" producer en een consumer

**Doel:** In dit lab gaan we aan de slag met Kafka, we zullen met tooling en als laatste stap met een Java programma maken om berichten van en naar Kafka te sturen.

Clone deze git-repo:

    $ git clone git@github.com:axonxai/kafka101_workshop.git

of Download de zip met een browser: https://github.com/axonxai/kafka101_workshop/archive/master.zip
    
We beginnen met het starten van onze kafka setup op, open een terminal en ga naar de directory 'cp-all-in-one' en start de docker images:

    $ cd cp-all-in-one
    $ docker-compose up

Je ziet een hoop gepruttel, dit is goed :) Laat de terminal met rust en open een andere terminal waar je mee verder gaat!

Controleer of je ongeveer dezelfde output ziet als hieronder met het volgende docker commando

    $ docker ps

    CONTAINER ID        IMAGE                                             COMMAND                  CREATED             STATUS              PORTS                                              NAMES
    cdf381c63565        confluentinc/cp-enterprise-control-center:5.2.1   "/etc/confluent/dock…"   3 days ago          Up 2 minutes        0.0.0.0:9021->9021/tcp                             control-center
    03b85067d06f        confluentinc/cp-ksql-cli:5.2.1                    "/bin/sh"                3 days ago          Up 2 minutes                                                           ksql-cli
    b4e2cabcafc2        confluentinc/ksql-examples:5.1.2                  "bash -c 'echo Waiti…"   3 days ago          Up 2 minutes                                                           ksql-datagen
    21b2f105787f        confluentinc/cp-ksql-server:5.2.1                 "/etc/confluent/dock…"   3 days ago          Up 2 minutes        0.0.0.0:8088->8088/tcp                             ksql-server
    0ccfcca6434c        confluentinc/cp-kafka-rest:5.2.1                  "/etc/confluent/dock…"   3 days ago          Up 2 minutes        0.0.0.0:8082->8082/tcp                             rest-proxy
    3eecab329713        confluentinc/kafka-connect-datagen:latest         "bash -c 'if [ ! -d …"   3 days ago          Up 2 minutes        0.0.0.0:8083->8083/tcp, 9092/tcp                   connect
    6b03d0385e7f        confluentinc/cp-schema-registry:5.2.1             "/etc/confluent/dock…"   3 days ago          Up 3 minutes        0.0.0.0:8081->8081/tcp                             schema-registry
    5f6603ae7ff0        confluentinc/cp-enterprise-kafka:5.2.1            "/etc/confluent/dock…"   3 days ago          Up 3 minutes        0.0.0.0:9092->9092/tcp, 0.0.0.0:29092->29092/tcp   broker
    87d4ea06496e        confluentinc/cp-zookeeper:5.2.1                   "/etc/confluent/dock…"   3 days ago          Up 3 minutes        2888/tcp, 0.0.0.0:2181->2181/tcp, 3888/tcp         zookeeper

We moeten nu het gateway IP adres (docker host ip) achterhalen

    $ docker inspect <container id van confluentinc/cp-enterprise-kafka:5.2.1 >

```Note: Dit noemen we in de volgende oefeningen <ip-adres> !!!```

## Berichten sturen met de CLI

Ondanks dat kafka default staat ingesteld om automatisch een topic aan te maken indien die nog ontbreekt, is het een 'best practice' om topic klaar te zetten, je hebt dan de mogelijkheid om je replicatie en andere properties in te stellen per topic.

### Een topic creëren, doen we op de volgende manier
    $ kafka-topics --zookeeper <ip-adres>:2181 --create --replication-factor 1 --partitions 1 --topic hello_world

Als het goed is zie je nu je aangemaakte topic (met een hoop al bestaande internal topic):

    $ kafka-topics --zookeeper <ip-adres>:2181 --list

### Een bericht sturen

    $ kafka-console-producer --broker-list <ip-adres>:9092 --topic hello_world
    > hallo

Nu kun je een reeks berichten sturen naar dit topic, je krijgt een prompt > en zolang je niet op <ctrl>-c drukt blijf je berichten sturen.

### Een bericht bekijken
Open nu een nieuwe terminal en start een consumer met:

    $ kafka-console-consumer --bootstrap-server <ip-adres>:9092 --topic hello_world

Nu kun je het topic uitlezen en komen hier nieuwe berichten voorbij. 
Als je alle berichten wilt zien voeg je `--from-beginning` toe aan het commando. 

We hebben nu de standaard Kafka tooling gebruikt om berichten te sturen en uit te lezen uit een topic, als laatste onderdeel in dit lab gaan we dit nu doen met een stukje Java. 

```Note: de antwoorden staan ook in het zelfde lab, mocht je vastlopen kun je hier spieken.```

## Lets code een producer in java
Open je favoriete editor en importeer de producer-directory,

Als het goed is zie je nu de structuur van een 'Maven' project met de code onder de src-directory. Ga naar src/main/java/ai.axonx.workshop.Producer.java

Volg de inststructies op in de punten in deze class, voor het ip adres gebruiken we nu localhost, mocht je onder virtualbox draaien dan kun je of via DOCKER_HOST_IP of een NAT rule maken om vanuit je laptop een connectie te maken richting de brokers

Als je klaar bent met de changes dan kun je alles via je IDE compileren of handmatig comppileren in een terminal met:

    $ mvn clean install

Er wordt een 'Fat' jar gebouwd, die je direct kan runnen. Start het java programma met:


    $ java -jar ./target/MyProducer-1.0-SNAPSHOT-jar-with-dependencies.jar


## Lets code een Consumer in java

Zelfde als Producer lab, volg de instruties op in de code en zie de berichten van de Producer nu via de Consumer code op de console output geprint. Ook nu weer is via Maven een 'Fat' jar beschikbaar.

    $ java -jar ./target/MyConsumer-1.0-SNAPSHOT-jar-with-dependencies.jar

Nu we een idee hebben hoe we met Java een producer/consumer kunnen maken, gaan we verder met onze ontdekkingstocht rond Kafka :)
 
 [Next Lab](https://github.com/axonxai/kafka101_workshop/tree/iteratie_01/lab_02)
