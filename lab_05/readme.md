# LAB-05 Twitter schema uitbreiden met user

-   Breid je huidige schema aan met "user", zorg ervoor dat je je versienummer aanpast
-   Key toevoegen aan je producer. De key is de "user" / poster van de tweet
-   Producer aanpassen om key + message uit te lezen van je topic
-   Pas je producer/consumer aan voor het nieuwe schema
-   Laten ervaren [forward/backward compatibility opties](https://docs.confluent.io/current/schema-registry/avro.html#summary). De te kiezen strategie hangt af van de gewenste aanpassing in het schema:
  - Als er alleen optionele velden worden toegevoegd, dan voldoet de default: `BACKWARD`. Geupdate v2 consumers kunnen v1 berichten lezen. Verwijderde velden worden ook ondersteund - de eventuele inhoud wordt dan gewoon genegeerd.
  - Als er ook verplichte velden worden toegevoegd, kies dan `FORWARD`. Berichten van v2 producers kunnen dan nog worden verwerkt door v1 consumers. Verwijderde velden worden alleen ondersteund als ze optioneel waren - ze worden dan beschouwd als afwezig.
  - Verplichte velden kunnen optioneel gemaakt worden door een default-waarde toe te voegen die gebruikt wordt bij afwezigheid.
  - Als je het type van een veld aanpast is dat altijd een breaking change (`NONE`).
  - TODO: uitwerken `FULL` compatibility. Wat is een voorbeeld van "Modify optional fields"?
