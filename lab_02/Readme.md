# Lab 2

In dit lab gaan we allereerst aan de slag om van twitter tweets naar Kafka te sturen (topic twitter_tweets)

Je hebt een account op twitter, hier mee kun je ook een developer account aanvragen bij twitter, ga naar de volgende URL: https://developer.twitter.com/content/developer-twitter/en.html

Meld je aan en voeg een app toe, dit wordt ons java programma wat tweet via de API gaat uitlezen.

Open je editor en voeg je credentials toe in de java code,

Daarna kun je alles compileren met:

$ mvn clean install

Nu starten we het java programma, ga hiervoor op de cmdline naar targets en run de 'fat' jar met:

$ java -jar kafka-producer-twitter-1-jar-with-dependencies.jar

