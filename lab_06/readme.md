# LAB-06 KSQL & filter tweets
In dit lab gaan we aan de slag met KSQL, de query language van Confluent. Hiermee is het bv. mogelijk om op streaming data analyses uit te voeren of om statische data en streaming data in een join te querien. 

## Een overzicht van de actiefste users

start de ksql-cli
```ksql```

Om een stream te maken van het topic maken we een 1-op-1 kopie van het topic "trump_tweets". Wel maken we een stream met een gefilterd aantal attributen. 
```sh
$ CREATE STREAM kstream_twitter_tweets
    (created_at VARCHAR, 
    id BIGINT,
    user STRUCT <
        name VARCHAR, 
        location VARCHAR, 
        followers_count INTEGER>, 
    text VARCHAR) 
    WITH (KAFKA_TOPIC='twitter_tweets',  VALUE_FORMAT='JSON');

# create an enriched stream with user_name
$ CREATE STREAM kstream_twitter_tweets_enriched AS
SELECT *, user->name as user_name
FROM kstream_twitter_tweets PARTITION BY user_name;
```

Als het goed is krijg je nu een melding "stream is created" of iets dergelijks.

Om nu de inhoud van deze stream te bekijken, kun je via de ksql cli het volgende doen:
```sh
$ SET 'auto.offset.reset'='earliest';
$ SELECT * FROM kstream_twitter_tweets_enriched;
```

Maak nu een tabel met users en aantal tweets, en bekijk deze tabel
```sh
$ CREATE TABLE ktable_user_tweet_count AS
  SELECT user_name, COUNT(*) AS tweet_count
  FROM kstream_twitter_tweets_enriched 
  GROUP BY user_name;
$ SELECT * from ktable_user_tweet_count;
```

Dit gaan we nu uitbreiden omdat we alleen geïnteresseerd zijn in de meest actieve gebruikers op dit moment, dit doen we o.b.v. een tumbling window
```sh
#verwijder de oude tabel
$ DROP TABLE ktable_user_tweet_count;
# Waarschijnlijk krijg je een melding dat er eerst een andere query moet verwijderen, dit doe je met 'TERMINATE <query>'

$ CREATE TABLE ktable_user_tweet_count AS
  SELECT user_name, COUNT(*) AS tweet_count
  FROM kstream_twitter_tweets_enriched WINDOW TUMBLING (SIZE 1 MINUTES)
  GROUP BY user_name;
```
Voor de overzichtelijkheid kun je natuurlijk nog zoiets toevoegen als `WHERE tweet_count > 3`


## Let's make the Filter "great"

```sh
$ CREATE STREAM kstream_twitter_tweets_with_filter AS
SELECT *
FROM kstream_twitter_tweets_enriched
WHERE text LIKE '% great %';
```


## We willen weten welke actieve users bepaalde woorden tweeten - Join - 
### DIT WERKT NOG NIET

```sh
$ CREATE STREAM kstream_twitter_tweets_with_filter_activeusers AS
  SELECT * 
  FROM kstream_twitter_tweets_with_filter
  INNER JOIN KTABLE_USER_TWEET_COUNT ON 
    kstream_twitter_tweets_with_filter.user_name = ktable_user_tweet_count.user_name;
```
