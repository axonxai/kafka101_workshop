# LAB-04 intermezzo Avro Schema's  

DOEL: Handson met avro schema voor event message 

Een Kafka broker houdt zich bezig met streaming van de events en doet zelf geen inspectie van de data die over de topics wordt verstuurd. Sterker nog Kafka laadt de streaming data niet eens in memory, het gebruikt het concept ZERO-COPY en dit alles je raadt het al voor de snelheid!

Dus Kafka handelt in bytes en past geen verificatie toe.

Als je binaire data tussen 2 partijen probeer je doormiddel van encoding/decoding precies aan te geven als je bijvoorbeeld een Integer wilt over sturen in hoeveel bits je dit doet, de ontvanger weet dan precies met hoeveel bits hij om moet zetten om de Integer waarde te kunnen weergeven.
Als we geen extra informatie meegeven beschouwd Kafka alles als String, dit is niet efficient Integers worden opgeknipt als Strings per digits, dit kan veel effienter in 2 bytes. 
Vroeger had je vaak in een protocol stack hardwarematige enconding/decoding, dit was niet flexible, rond 2000 zijn encoding/decodings in de applicatie layers belandt, hierdoor was je flexibel bv SOAP/XML. Flexibel was het zeker, maar minder efficient qua transport en vaak door extra parsing CPU intensief. Je ziet de laatste jaren weer een versimpeling en terug grijpen naar byte level (de)serializeren. 
Er zijn vele  (de)serializeren frameworks zoals avro, Protobuf, Thrift, Parque, ORC zo doen ongeveer allemaal hetzelfde en hebben voor bepaalde use cases een voorkeur. Avro is veel gebruikt in het Apache eco system en is razend snel en wordt ook gebuikt als first-citizen voor (de)serializeren.

Avro is gedefineerd als een Schema in JSON en heeft de volgende eigenschappen:
- Data fully typed
- Data is gecomprimeerd  
- 
- mechanisme voor schema evolutie in de tijd





De Kafka Registry is een Open-Source project van Confluents wat het mogelijk maakt om vooraf een akkoord te maken of de data stromen binnen Kafka doormiddel van een schema, in bijzonder Avro Schema's