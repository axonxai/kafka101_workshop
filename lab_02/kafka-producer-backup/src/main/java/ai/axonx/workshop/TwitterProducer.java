package ai.axonx.workshop;

import com.google.common.collect.Lists;
import javafx.util.Pair;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TwitterProducer {

    // Logger
    Logger logger = LoggerFactory.getLogger(TwitterProducer.class.getName());

    // Subscribe on Tweet subjects
    List<String> terms = Lists.newArrayList("obama", "hillary", "pocahontas", "poor");

    //private Client client;
    private WebTarget target;


    public TwitterProducer() {
    }

    public static void main(String[] args) {
        new TwitterProducer().run();
    }

    public void run() {

        logger.info("Setup");

        /** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(1000);

        // Create array list to store tweets in
        ArrayList<Pair<String, String>> tweets = new ArrayList<Pair<String, String>>();

        // Search all serachterms and read twitter posts
        for (String term : terms) {
            logger.info("Search for " + term + " in Trumps tweets.");
            ReadTwitterPost(term, 1, tweets);
        }

        // create a kafka producer
        KafkaProducer<String, String> producer = createKafkaProducer();

        // add a shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("closing producer...");
            producer.close();
            logger.info("done!");
        }));

        // loop to send tweets to kafka
        // on a different thread, or multiple different threads....
        for (Pair<String, String> tagTweet : tweets) {
            logger.info(tagTweet.getKey());
            producer.send(new ProducerRecord<>("twitter_tweets", null, tagTweet.getKey()), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e != null) {
                        logger.error("Something bad happened", e);
                    }
                }
            });
        }

        logger.info("End of application");
    }


    public void ReadTwitterPost(String searchPattern, int page, ArrayList<Pair<String, String>> tweets) {
        Client client = ClientBuilder.newClient();

        target = client.target("https://api.tronalddump.io/search/quote?query=" + searchPattern + "&page=" + page);
        client.register(TwitterPostReader.class);
        Invocation.Builder invocationBuilder
                = target.request(MediaType.APPLICATION_JSON);

        // Get response
        Response response = target.request().get();

        // Parse values
        TwitterPost values = response.readEntity(TwitterPost.class);
        response.close();
        client.close();

        if (tweets == null)
            tweets = new ArrayList<>();

        tweets.addAll(values.QuotesTags);

        logger.info("Got range " + (25 * page) + " tweets of search term total " + values.total + ".");

        if ((25 * page) < values.total) {
            ReadTwitterPost(searchPattern, page + 1, tweets);
        }
    }

    public KafkaProducer<String, String> createKafkaProducer() {
        String bootstrapServers = "127.0.0.1:9092";

        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create safe Producer
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
        properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");
        // high throughput producer (at the expense of a bit of latency and CPU usage)
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20");
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32 * 1024)); // 32 KB batch size

        // create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        return producer;
    }
}
