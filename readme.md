# Workshop plan

1. Hello-world producer en consumer. Ook key toevoegen
2. Twitter API - Sniffer. + kafka-console-consumer om berichten uit te lezen. 
3. Bericht schema maken voor Twitterbericht {timestamp, message}. Uitlezen van het nieuwe topic met schema via KSQL
4. Brokers onderuit halen en kijken wat er gebeurt. 
5. Schema uitbreiden met user. Forward an backward compatibility aantonen.
6. KSQL stream maken met scheld-tweets. Ktable maken met aantallen berichten per user (top-users) met tumbling window.
7. Kafka connect - inlezen SQL lite data - tabel met Amerika synoniemen en scheldwoorden. 
8. KSQL Join query met ingeladen tabellen om woorden in de berichten te gaan vervangen met alternatieve woorden en op een nieuwe stream zetten. 
9. Streaming API - ??
10. Kafka REST API
11. Compacting


