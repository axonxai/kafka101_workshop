[Next Lab](https://github.com/axonxai/kafka101_workshop/tree/master/lab_02)

# LAB-01 Maak een Kafka "hello world" producer en een consumer

**Doel:** In dit lab gaan we aan de slag met Kafka, we zullen met tooling en als laatste stap met een Java programma maken om berichten van en naar Kafka te sturen.

De asumptie is dat je de voorbereiding hebt gevolgd; de volgende stap is het achterhalen van het gateway IP adres (docker host ip).

    $ docker inspect <container id van confluentinc/cp-enterprise-kafka:x.x.x >

```Note: Dit noemen we in de volgende oefeningen <ip-adres> !!!```

## Berichten sturen met de CLI

Ondanks dat Kafka default staat ingesteld om automatisch een topic aan te maken indien die nog ontbreekt, is het een 'best practice' om topic klaar te zetten, je hebt dan de mogelijkheid om je replicatie en andere properties in te stellen per topic.

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

## Let's code: een Producer in Java

Open je favoriete IDE en importeer de producer-directory,

Gebruik voor de oefeningen de JavaDocs van Confluent Kafka: https://kafka.apache.org/0100/javadoc/index.html?index-all.html

Als het goed is zie je nu de structuur van een 'Maven' project met de code onder de src-directory. Ga naar src/main/java/ai.axonx.workshop.Producer.java

Volg de inststructies op in de punten in deze class, voor het ip adres gebruiken we nu localhost, mocht je onder virtualbox draaien dan kun je of via DOCKER_HOST_IP of een NAT rule maken om vanuit je laptop een connectie te maken richting de brokers

Als je klaar bent met de changes dan kun je alles via je IDE compileren of handmatig compileren in een terminal met:

    $ mvn clean install

Er wordt een 'Fat' jar gebouwd, die je direct kan runnen. Start het java programma met:


    $ java -jar ./target/MyProducer-1.0-SNAPSHOT-jar-with-dependencies.jar


## Let's code: een Consumer in Java

Hetzelfde als Producer lab, volg de instructies op in de code en zie de berichten van de Producer nu via de Consumer in de console output geprint. Ook nu weer is via Maven een 'Fat' jar beschikbaar.

    $ java -jar ./target/MyConsumer-1.0-SNAPSHOT-jar-with-dependencies.jar

Nu we een idee hebben hoe we met Java een producer/consumer kunnen maken, gaan we verder met onze ontdekkingstocht rond Kafka :)
 
 [Next Lab](https://github.com/axonxai/kafka101_workshop/tree/iteratie_01/lab_02)
