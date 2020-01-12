package ai.axonx.workshop;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.util.Pair;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.ws.rs.client.Invocation;
import javax.xml.crypto.dsig.keyinfo.KeyValue;

/**
 *
 * @author Démian Janssen
 */
public class TwitterProducer {

    private static Properties properties;
    private static KafkaProducer<String, AvroTweet> producer;

    private static List<String> terms;
    private static BlockingQueue<String> msgQueue;


    public static void init(List<String> topics, String brokers) {

        terms = topics;
        msgQueue = new LinkedBlockingQueue<>();

        properties = DemoProperties.getProperties(brokers);
        producer = new KafkaProducer<>(properties);

        // Create a hasmap for tweets concerning certain topics
        Map<String, ArrayList<ai.axonx.workshop.quotes_record>> searchTweets = new HashMap<>();
        ArrayList<ai.axonx.workshop.quotes_record> tweets;

        // Search all serach terms and read twitter posts
        for (String term : terms) {
            System.out.println("Search for " + term + " in Trumps tweets.");
            tweets = new ArrayList<>();
            ReadTwitterPost(term, 1, tweets);
            searchTweets.put(term, tweets);
        }

        System.out.println("Done fetching tweets");

        for (Map.Entry<String, ArrayList<ai.axonx.workshop.quotes_record>> entry : searchTweets.entrySet()) {
            String searchTerm = entry.getKey();

            int key = 0;
            for (ai.axonx.workshop.quotes_record tweet : entry.getValue()) {
                AvroTweet avroTweet;
                // try {

                avroTweet = new AvroTweet();

                // Put values in our avrotweet
                ai.axonx.workshop.user user = new user();
                user.put("name", tweet.getEmbedded$1().getAuthor().get(0).getName());

                avroTweet.put("text", tweet.getValue());
                avroTweet.put("user",user);

                // Add some extra fields to the avro schema
                // Think about date, perhaps some more info about the user (in this case Donald Trump)
                // You could also perhaps add some data about his target of interest (tags)
                // Check the example json for ideas

                ProducerRecord<String, AvroTweet> record = new ProducerRecord<>(
                        searchTerm, Integer.toString(key), avroTweet
                );

                    producer.send(record);

                System.out.println(String.format("topic=%s", searchTerm));
                System.out.println(String.format("value=%s", avroTweet.toString()));
                key++;
            }
        }
    }

    public static void ReadTwitterPost(String searchPattern, int page, java.util.List<ai.axonx.workshop.quotes_record> tweets) {
        if (tweets == null)
            throw new IllegalArgumentException("Argument tweets can't be null");

        Client client = ClientBuilder.newClient();

        WebTarget target = client.target("https://api.tronalddump.io/search/quote?query=" + searchPattern + "&page=" + page);
        client.register(String.class);
        Invocation.Builder invocationBuilder
                = target.request(MediaType.APPLICATION_JSON);

        Gson gson = new Gson();

        // Get response
        Response response = target.request().get();

        // Put json response in string
        String tweet = response.readEntity(String.class);

        // Parse json
        TronaldDump value = gson.fromJson(tweet, TronaldDump.class);

        response.close();
        client.close();

        // Get tweets
        tweets.addAll(value.getEmbedded$1().getQuotes());

        // Get tweets recursive (fake pagination)
        if ((25 * page) < value.getTotal()) {

            // Don't overtax the api
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interrupt) {
                System.out.println(interrupt);
            }

            // Get next 25 tweets
            ReadTwitterPost(searchPattern, page + 1, tweets);
        }

        System.out.println("Got range " + (25 * page) + " tweets of search term total " + value.getTotal() + ".");
    }
}
