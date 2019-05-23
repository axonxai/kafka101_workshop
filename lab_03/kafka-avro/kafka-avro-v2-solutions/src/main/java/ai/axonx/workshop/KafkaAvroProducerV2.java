package ai.axonx.workshop;

import com.google.gson.Gson;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaAvroProducerV2 {

    private static Gson gson = new Gson();

    private static TweetLikes metGson(String tweet) {
        TweetLikes fromJson = gson.fromJson(tweet, TweetLikes.class);
        return fromJson;
    }

    /*
     * met de builder moet je zelf ervoor zorgen dat de juiste informatie op de juiste plaats terecht komt
     * dan heb je wel meer vrijheid in het ontwerpen van de Avro schema's, maar het kost meer moeite
     */
    private static TweetLikes metBuilder(String tweet) {
        String user_name = "name";
        String user_screenname = "alias";
        boolean user_verified = false;
        TweetLikes tweetLikes = TweetLikes.newBuilder()
                .setUser(new user(user_name, user_screenname, user_verified))
                .setTimestampMs(12345678)
                .setFavoriteCount(455)
                .setText("tweet message")
                .build();

        return tweetLikes;
    }

    public static void main(String[] args) {
        Properties properties = new Properties();
        // normal producer
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("acks", "all");
        properties.setProperty("retries", "10");
        // avro part
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        properties.setProperty("schema.registry.url", "http://127.0.0.1:8081");

        Producer<String, TweetLikes> producer = new KafkaProducer<String, TweetLikes>(properties);

        String topic = "avro-tweet";

        TweetLikes tweetLikes = metGson("{}");

        ProducerRecord<String, TweetLikes> producerRecord = new ProducerRecord<String, TweetLikes>(
                topic, tweetLikes
        );

        System.out.println(tweetLikes);
        producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception == null) {
                    System.out.println(metadata);
                } else {
                    exception.printStackTrace();
                }
            }
        });

        producer.flush();
        producer.close();
    }
}
