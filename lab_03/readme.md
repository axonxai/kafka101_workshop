# LAB-03 AVRO schema's, met Twitterberichten

## Leerdoelen
- AVRO schema's leren kennen, eentje opstellen
- AVRO serialization gebruiken met Kafka

## Stappenplan voor de workshop

## Stap 1

Bepaal welke data we willen verwerken.
Aan de hand van een voorbeeld tweet (JSON) kunnen we verkennen welke data er beschikbaar is. 
We willen minstens de tekst van de tweet zelf.

## Stap 2

Maak een syntactisch correct schema dat aan je wensen voldoet.

## Stap 3

Maven build zodat `maven-avro-plugin` het werk voor je doet. 
Verifieer je AVRO schema door met Gson een Tweet automagisch te parseren naar de door AVRO gegenereerde Java class.

## Stap 4

Vervang String serialization met AVRO serialization waar mogelijk:
- KafkaProducer
- Properties
- KafkaConsumer, voor de liefhebbers

# 'Inspiratie'

- In `twitter/src/main/avro` staan twee AVRO schema's, eentje met een simpele key-value structuur (nog geen map) en de ander als voorbeeld voor een Twitter bericht.
- Maak een nieuw AVRO schema 'from scratch' of gebruik de voorbeeldschema's om een nieuw AVRO schema te maken.
- in `twitter/pom.xml` zie je dat er vier dependencies zijn:
    - org.apache.kafka kafka-clients
    - com.google.code.gson gson
    - org.apache.avro avro
    - com.twitter hbc-core
- Naast deze dependencies is de avro-maven-plugin ook benodigd om het project te bouwen, deze zal de AVRO schema's gebruiken om automagisch Java klassen te maken. Deze worden vervolgens gebruikt om met AVRO te serializen.
- In `twitter/src/main/java/ai/axonx/kafkademo/` staan de `java` bestandjes, het leukste (en meest relevante voor de workshop) gebeurt in de TwitterProducer, deze neemt twitter berichten, filtert de twitter JSON aan de hand van het AVRO schema op enkele velden, en geeft deze door aan een Kafka broker. Voor de opdracht is het bijvoorbeeld interessant om deze Kafka berichten met AVRO serialization te laten sturen in plaats van met String serialization op JSON zoals nu het geval is.


# Extra

- Wat gebeurt er als je 'foute' berichten stuurt die niet in het schema passen?
- Op welke manier(en) kan je omgaan met forward en backward compatibility?

