[Previous Lab](https://github.com/axonxai/kafka101_workshop/tree/iteratie_01/lab_02) | [Next Lab](https://github.com/axonxai/kafka101_workshop/tree/iteratie_01/lab_06)

# LAB-03 intermezzo AVRO schema's, met Twitteren met AVRO Producers/Consumers

**Doel:** Handson met avro schema's voor event messaging
 - Avro schema's opstellen
 - Avro serialization gebruiken met Kafka

Een Kafka broker houdt zich bezig met streaming van de events en doet zelf geen inspectie van de data die over de topics wordt verstuurd (geen CPU processing). Sterker nog Kafka pakt de streaming data, maar niet in memory (ZERO-COPY concept) en dit alles je raadt het al voor de snelheid!

Dus Kafka handelt in bytes-streams en past geen verificatie toe. Dit is uiteindelijk wel wenselijk en kunnen we realiseren met de Kafka Registry, maar eerst even meer over de bytes-streams en schema's.

Als je binaire data tussen 2 partijen over de 'wire' wilt sturen (serializing/deserializing)  moet je beschrijven met een data format hoe je data types eruit zien (encoding/decoding). 

Voorbeeld wij willen het getal '8431' oversturen, als we in Kafka geen schema's gebruiken wordt alles omgezet naar String, dus het karakter '8' in utf-8 heeft standaard 2 bytes nodig, enz dus totaal 8 bytes. Met een dataformat geef je aan dit is een getal en 843 wordt dan in 1 Integer (32 bits), dus 4 bytes overgestuurd. Dit scheelt dus 4 bytes!
Als het volume of de grote van de berichten toenemen zie je de voordelen van het gebruik van encoding. 

Binnen Kafka is gekozen voor Avro. Avro encoding is beschreven als een JSON schema, en heeft de volgende voordelen:
- Data is fully typed
- Data is compressed 
- Schema (JSON) komt met de data
- Documentatie is onderdeel in het schema
- Data language agnostic (geen harde binding met een bepaalde taal)
- Schema evolution, mechanisme om veranderingen in de data toe te staan 

## Avro Record Schema structuur
Een Avro schema heeft een record structuur als volgt:

    Name 
    Namespace
    Doc
    Aliases
    Fields
       Name
       Doc
       Type

voorbeeld:

    {
        "type": "record",
        "namespace": "ai.axonx.workshop",
        "name": "tweet",
        "fields": [
            { "name": "body", "type": "string" },
            { "name": "id", "type": "int"}
        ]
    }

Bovenstaande record voorbeeld noemen we een Avro Schema en wordt als een file met extentie .avsc opgeslagen.

 ## Avro Primitive Types

We kennen de volgende types voor de fields elementen:

    null : geen waarde
    boolean
    int  : 32 bits signed
    long : 64 bits signed
    float: 32 bits 
    double : 64 bits
    byte : 8 bits
    string : unicode

 ## Avro Complex Types
  
    enums  =>  { "name": "build_success", "type": "enum", "symbols": ["ROOD", "GROEN"] }
    arrays =>  { "name": "relations", "type": "array", "items": "string" }
    maps   =>  { "name": "hashmap", "type": "maps", "values": "string" }
    unions =>  { "name": "tussenvoegsel", "type": ["null", "string"], "default": null }   
    type is een ander schema (incl namespace)

    **Let op:** default waarde type is gelijk aan eerste type union!

## Logical Types

    decimals (bytes)        : geld 
    date (int)              : aantal dagen sinds 1970 
    time-millis (long)      : millisec na midnight
    timestamp-millis (long) : millisec sinds 1970

Voorbeeld gebruik logical type voor onze tweets: 

    {"name": "tijd_tweet", "type": "long", "logicalType": "timestamp-millis"}

### Oefening 1 Maak een Avro schema

Check Tweet Object op: https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/tweet-object.html

Onder lab_03 in de directory 'avro-oefeningen' vind je een leeg schema 'tweet-likes-v1.avcs'. Vul dit Avro schema aan, gebruik uit het orginele Tweet Object de volgende attributten:

    created_at
    text
    favorite_count

over te nemen in het avro schema en let op de types!

We zullen dit nu onze V1 (version 1) noemen van onze Tweet Stream berichten. 

De volgende stap is om een V2 versie te maken het schema, we slaan deze uitbreiding even apart op,








## Kafka Registry








### Oefening 2 Kafka Avro Producer

Spiek nog even in Lab_02 naar de Producer code, we gaan nu avro schema validatie toepassen. In de directory Lab_03/twitter vind je de voorbereidingen voor avro




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

[Previous Lab](https://github.com/axonxai/kafka101_workshop/tree/iteratie_01/lab_02) | [Next Lab](https://github.com/axonxai/kafka101_workshop/tree/iteratie_01/lab_06)