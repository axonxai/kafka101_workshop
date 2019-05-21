package ai.axonx.workshop;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;

/**
 *
 * @author Démian Janssen
 */
public class TwitterProducer {

    private static Properties properties;
    private static KafkaProducer<String, String> producer;

    private static Client client;
    private static List<String> terms;
    private static BlockingQueue<String> msgQueue;

    private static Thread thread;

    public static void init(List<String> topics, String brokers) {

        terms = topics;
        msgQueue = new LinkedBlockingQueue<>();

        properties = DemoProperties.getProperties(brokers);
        producer = new KafkaProducer<>(properties);

        Hosts hosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();

        endpoint.trackTerms(terms);

        // Let op dat de credentials ingevuld worden
        Authentication auth = new OAuth1(
                TwitterCredentials.consumerKey,
                TwitterCredentials.consumerSecret,
                TwitterCredentials.token,
                TwitterCredentials.secret);

        ClientBuilder builder = new ClientBuilder()
                .hosts(hosts)
                .authentication(auth)
                .endpoint(endpoint)
                .processor(new StringDelimitedProcessor(msgQueue));

        client = builder.build();
        client.connect();

        // Het is netter om hier een aparte class voor te maken
        thread = new Thread() {
            @Override
            public void run() {
                // Topic aanpassen aan de hand van zoektermen
                String topic = "twitter";
                topic = terms.stream().map((term) -> "-" + term).reduce(topic, String::concat);
                String tweet = null;
                int key = 0;
                Gson gson = new Gson();

                while (true) {
                    try {
                        tweet = msgQueue.poll(5, TimeUnit.SECONDS);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TwitterProducer.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (tweet != null) {
                        System.out.println(String.format("polled tweet=%s\n", tweet));

                        AvroTweet value;
                        try {
                            value = gson.fromJson(tweet, AvroTweet.class);

                            try {
                                // Momenteel gebruiken we Gson slechts als een JSON parser om een aantal 
                                // interessante velden eruit te filteren, vervolgens sturen we het weer 
                                // door als een String. Dit is niet optimaal, want er is AVRO serialization.
                                // TODO: In plaats van dat de Producer String : String records stuurt
                                // moet het met AVRO serialization gaan werken. 
                                // Dus wordt het o.a. ProducerRecord<String, AvroTweet> 

                                ProducerRecord<String, String> record = new ProducerRecord<>(
                                        topic, Integer.toString(key), value.toString()
                                );
                                producer.send(record);

                                System.out.println(String.format("topic=%s", topic));
                                System.out.println(String.format("value=%s", value.toString()));

                                // We pollen + sturen elke paar seconden iets, puur om te testen
                                // De API van Twitter niet al te veel stresstesten
                                Thread.sleep(2000);
                                key++;
                            } catch (InterruptedException interrupt) {
                                System.out.println(interrupt);
                            }
                        } catch (JsonSyntaxException ex) {
                            System.out.println("Bad syntax" + ex);
                        }
                    }
                }
            }
        };
    }

    public static void produce() {
        thread.start();
    }
}
