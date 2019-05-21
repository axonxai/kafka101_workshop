# LAB-03 Bericht schema maken voor een Twitterbericht

-   Maak een avsc file met het schema en sla deze op de locatie op "/link/to/directory.avsc"
-   Zorg ervoor dat je schema de volgende onderdelen bevat "twitter-message" en "tijdstip" van de post 
-   Pas je producer aan om het schema te gebruiken en berichten in dat formaat naar Kafka te sturen
-   Wat gebeurt er als je 'foute'  berichten stuurt die niet in het schema passen?
-   

# Toevoeging -- van afgelopen weekend -- nog even toelichten :: verwerken in Lab 3 zodat er een rode draad is

- In `twitter/src/main/avro` staan twee AVRO schema's, eentje met een simpele key-value structuur (nog geen map) en de ander als voorbeeld voor een Twitter bericht.
- in `twitter/pom.xml` zie je dat er vier dependencies zijn: 
    - org.apache.kafka kafka-clients
    - com.google.code.gson gson
    - org.apache.avro avro
    - com.twitter hbc-core
- Naast deze dependencies is de avro-maven-plugin ook benodigd om het project te bouwen, deze zal de AVRO schema's gebruiken om automagisch Java klassen te maken. Deze worden vervolgens gebruikt om met AVRO te serializen.
- In `twitter/src/main/java/ai/axonx/kafkademo/` staan de `java` bestandjes, het leukste (en meest relevante voor de workshop) gebeurt in de TwitterProducer, deze neemt twitter berichten, filtert de twitter JSON aan de hand van het AVRO schema op enkele velden, en geeft deze door aan een Kafka broker. Voor de opdracht is het bijvoorbeeld interessant om deze Kafka berichten met AVRO serialization te laten sturen in plaats van met String serialization op JSON zoals nu het geval is. 

