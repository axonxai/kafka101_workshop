[Previous Lab](https://github.com/axonxai/kafka101_workshop/blob/master/lab_01/readme.md) | [Next Lab](https://github.com/axonxai/kafka101_workshop/blob/master/lab_03/readme.md)

# LAB-02 Data for real, Confluent Console 

Doel: Data ingestion van 'echte' bronnen (sources)

Het wordt tijd voor koppelen met 'echte data bronnen', een externe broker is gekoppeld met Twitter, we gaan aan de slag om deze tweets naar onze lokale Kafka te sturen (topic twitter_tweets).

Sinds Fake News een paar jaar geleden een hot topic is geworden,  is het niet zo makkelijk om een Twitter Dev account te fixen. 

Wij hebben daarom een Twitter Dev account draaien, die tijdens de de workshop tweets ophaalt die wij lokaal (iedere laptop) gaan gebruiken. 

<plaatje>

 Een developer account aanvraag bij twitter gaat via de volgende URL: https://developer.twitter.com/content/developer-twitter/en.html

We zullen nu de stappen als informatief laten zien, mocht je zelf een Dev account willen aanvragen. Log in op de Dev link en maak een App aan, dit wordt ons java programma wat tweets via de API gaat uitlezen. Navigeer naar Credentials tab, zie screendump 

![image](img/lab02_cred_tw.png "credentials")

Open je editor en voeg je credentials toe in de java code,

Daarna kun je alles compileren met:

    $ mvn clean install

Nu starten we het java programma, Run het via je IDE of ga hiervoor op de cmdline naar targets en run de 'fat' jar met:

    $ java -jar kafka-producer-twitter-1-jar-with-dependencies.jar



## Oefening:

In vorige lab heb je een producer en consumer gemaakt, maak nu 1 opzet die uit remote broker tweets ophaalt en deze naar je lokaal kafka stuurt.

De externe Kafka broker kun je bereiken op:

    IP: 18.130.143.143:9092 met topic: twitter_tweets

Zie je de tweets binnen komen?

    $ kafka-console-consumer --bootstrap-server broker:9092 --topic twitter_tweets


Of, check met Confluent Management Console, check via de browser of je de Tweets ziet binnen komen: http://127.0.0.1:9021

In screendump zie je hoe je bij Topics de data stream kunt monitoren:

![image](img/lab02_check.png "check")

Okay, we hebben nu genoeg data, stop het Java programa en ga door naar het volgende lab...


[Previous Lab](https://github.com/axonxai/kafka101_workshop/blob/master/lab_01/readme.md) | [Next Lab](https://github.com/axonxai/kafka101_workshop/blob/master/lab_03/readme.md)

 
