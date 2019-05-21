# Workshop plan

1. ✅ Hello-world producer en consumer 
2. ✅ Twitter API - Sniffer 
3. Bericht schema maken voor Twitterbericht {timestamp, message}
    - Een voorbeeld schema staat in `lab_03/twitter/src/main/avro`, misschien willen we dat iedereen een eigen schema maakt aan de hand van een voorbeeld schema (?)
4. Brokers onderuit halen en kijken wat er gebeurt
5. Twitter-Schema uitbreiden met user. Forward en backward compatibility aantonen
6. ✅ KSQL stream maken met scheld-tweets. Ktable maken met aantallen berichten per user (top-users) met tumbling window.
7. Kafka connect - inlezen SQL lite data - tabel met Amerika synoniemen en scheldwoorden. 
8. KSQL Join query - vervang scheld-tweets door lieve tweets 
9. Streaming API - ??
10. Kafka REST API
11. Compacting


# Voorbereiding / installaties
-   Installeer "cp-all-in-one" / confluent community op je laptop
-   Git & Git bash
-   Docker
-   [Java JDK8](https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html?printOnly=1)
-   Your favourite IDE (visual code, intelij, sublime, atom, …)
-   [Dev account op Twitter](https://developer.twitter.com/) 

