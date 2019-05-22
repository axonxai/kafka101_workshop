# LAB-03 intermezzo AVRO schema's, met Twitteren met AVRO Producers/Consumers

DOEL: Handson met avro schema voor event messaging
 - AVRO schema's leren kennen, eentje opstellen
 - AVRO serialization gebruiken met Kafka

Een Kafka broker houdt zich bezig met streaming van de events en doet zelf geen inspectie van de data die over de topics wordt verstuurd. Sterker nog Kafka laadt de streaming data niet eens in memory, het gebruikt het concept ZERO-COPY en dit alles je raadt het al voor de snelheid!

Dus Kafka handelt in bytes en past geen verificatie toe.

Als je binaire data tussen 2 partijen probeer je doormiddel van encoding/decoding precies aan te geven als je bijvoorbeeld een Integer wilt over sturen in hoeveel bits je dit doet, de ontvanger weet dan precies met hoeveel bits hij om moet zetten om de Integer waarde te kunnen weergeven.
Als we geen extra informatie meegeven beschouwd Kafka alles als String, dit is niet efficient Integers worden opgeknipt als Strings per digits, dit kan veel effienter in 2 bytes. 
Vroeger had je vaak in een protocol stack hardwarematige enconding/decoding, dit was niet flexible, rond 2000 zijn encoding/decodings in de applicatie layers belandt, hierdoor was je flexibel bv SOAP/XML. Flexibel was het zeker, maar minder efficient qua transport en vaak door extra parsing CPU intensief. Je ziet de laatste jaren weer een versimpeling en terug grijpen naar byte level (de)serializeren. 
Er zijn vele  (de)serializeren frameworks zoals avro, Protobuf, Thrift, Parque, ORC zo doen ongeveer allemaal hetzelfde en hebben voor bepaalde use cases een voorkeur. Avro is veel gebruikt in het Apache eco system en is razend snel en wordt ook gebuikt als first-citizen voor (de)serializeren.

Avro is gedefineerd als een Schema in JSON en heeft de volgende eigenschappen:
- Data fully typed
- Data is gecomprimeerd  
- Embeded documentatie
?todo check
- mechanisme voor schema evolutie in de tijd


De Kafka Registry is een Open-Source project van Confluents die de Avro schema's huisvest voor het gebruik van schema's in de Broker topics.

todo plaatje concept

Avro Record Schema structuur:
- Name
- Namespace
- Doc
- ?
-Fields
--Name
--Doc
--Type



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

