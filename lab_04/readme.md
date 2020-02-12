# LAB-04 KSQL & filter tweets
In dit lab gaan we aan de slag met KSQL, de query language van Confluent. Hiermee is het bv. mogelijk om op streaming data analyses uit te voeren of om statische data en streaming data in een join te querien. 

## Een overzicht van de actiefste users

Open een nieuwe terminal en run het volgende commando:

    $ docker exec -ti ksql-cli bash

start de ksql-cli

    $ ksql http://ksql-server:8088

                  ===========================================
                  =        _  __ _____  ____  _             =
                  =       | |/ // ____|/ __ \| |            =
                  =       | ' /| (___ | |  | | |            =
                  =       |  <  \___ \| |  | | |            =
                  =       | . \ ____) | |__| | |____        =
                  =       |_|\_\_____/ \___\_\______|       =
                  =                                         =
                  =  Streaming SQL Engine for Apache Kafka® =
                  ===========================================

    Copyright 2017-2019 Confluent Inc.

    CLI v5.4.0, Server v5.4.0 located at http://ksql-server:8088

    Having trouble? Type 'help' (case-insensitive) for a rundown of how things work!

    ksql>

Voordat je begint is het belangrijk dat je zorgt dat alle streams vanaf het begin van een topic gaan consumen.

    $ SET 'auto.offset.reset'='earliest';
    Successfully changed local property 'auto.offset.reset' to 'earliest'. Use the UNSET command to revert your change.

Om een stream te maken van het topic maken we een 1-op-1 kopie van het topic "twitter_tweets" uit lab_02, waar we mbv een nieuwe stream maken door te filteren op de 'name' attribute: 

Stap 1:

    $ CREATE STREAM kstream_twitter_tweets
        (created_at VARCHAR, 
        id BIGINT,
        user STRUCT < name VARCHAR, 
        followers_count INTEGER>, 
        text VARCHAR) 
        WITH (KAFKA_TOPIC='twitter_tweets',  VALUE_FORMAT='JSON');

Stap 2:

    $ CREATE STREAM kstream_trump_tweets AS
        SELECT *, user->name as user_name
        FROM kstream_twitter_tweets WHERE text LIKE                'realDonaldTrump';

We kunnen nu een SQL gebruiken om door onze streaming data heen te browsen, met ctrl-c onderbreek je de query:

    $ SELECT * FROM kstream_trump_tweets EMIT CHANGES;


Maak nu een tabel met users en aantal tweets, en bekijk deze tabel

    $ CREATE TABLE ktable_user_tweet_count AS
        SELECT user_name, COUNT(*) AS tweet_count
       FROM kstream_trump_tweets GROUP BY user_name;

Dit is de dualiteit van Kafka, het is streaming maar ook tegelijker tijd een database: 

    $ SELECT * FROM ktable_user_tweet_count;

Je ziet dat dit een eindeloze query is, met CTRL-c kun je de boel stoppen.


D
## Let's make the Filter "great" again

    $ CREATE STREAM kstream_twitter_tweets_with_filter AS
        SELECT *
        FROM kstream_twitter_tweets_enriched
        WHERE text LIKE '% USA %';



== uitzoeken nog relavant?

it gaan we nu uitbreiden omdat we alleen geïnteresseerd zijn in de meest actieve gebruikers op dit moment, dit doen we o.b.v. een tumbling window

    #verwijder de oude tabel
    $ DROP TABLE ktable_user_tweet_count;

# Waarschijnlijk krijg je een melding dat er eerst een andere query moet verwijderen, dit doe je met 'TERMINATE <query>'

$ CREATE TABLE ktable_user_tweet_count AS
  SELECT user_name, COUNT(*) AS tweet_count
  FROM kstream_twitter_tweets_enriched WINDOW TUMBLING (SIZE 1 MINUTES)
  GROUP BY user_name;

Voor de overzichtelijkheid kun je natuurlijk nog zoiets toevoegen als `WHERE tweet_count > 3`


## OPDRACHT - We willen weten welke actieve users bepaalde woorden tweeten - Doe dit mbv een join tussen een stream en een table 

```sh
$ CREATE STREAM kstream_twitter_tweets_with_filter_activeusers AS
  SELECT * 
  FROM kstream_twitter_tweets_with_filter
  INNER JOIN KTABLE_USER_TWEET_COUNT ON 
    kstream_twitter_tweets_with_filter.user_name = ktable_user_tweet_count.user_name;
```
