# Workshop plan

1. ✅ Hello-world producer en consumer (TODO maak example naast solution die er staat! Oefening ProducerRecord laten maken door API doc op te zoeken, stap2 is dan het verzenden even alles key loos! Dit zijn de API Java docs die gebruikt moeten worden https://kafka.apache.org/0100/javadoc/index.html?index-all.html )
2. ✅ Twitter API - Sniffer (doel van dit lab om echte data te connecten, dus REST connectie via eigen producer, we gebruiken de data en mechnisme in de andere labs)
3. [bezig Bas, Demian] avro uitleg, registry, producer / consumer met avro
    - [] avro schema uitleg
    - []registry > kafka-avro-console-producer/consumer oefeningen breaking data ect
    - ✅twitter met avro, opzet Demian

4. [skip, gaan we niet meer doen lastig 1 broker in deze setup] Brokers onderuit halen en kijken wat er gebeurt
5. [skip, zit in lab3]Twitter-Schema uitbreiden met user. Forward en backward compatibility aantonen
6. ✅ KSQL stream maken met scheld-tweets. Ktable maken met aantallen berichten per user (top-users) met tumbling window.
7. Kafka connect - inlezen SQL lite data - tabel met Amerika synoniemen en scheldwoorden. 
8. KSQL Join query - vervang scheld-tweets door lieve tweets 
9. [skip] Streaming API - ??
10. [] Kafka REST API -> python REST call naar KAfka REST, simple voorbeeld richting twitterdata
11.[check haalbaarheid] Compacting


# Voorbereiding / installaties
-   Installeer "cp-all-in-one" / confluent community op je laptop
-   Git & Git bash
-   Docker
-   [Java JDK8](https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html?printOnly=1)
-   Your favourite IDE (visual code, intelij, sublime, atom, …)
-   [Dev account op Twitter](https://developer.twitter.com/) 

