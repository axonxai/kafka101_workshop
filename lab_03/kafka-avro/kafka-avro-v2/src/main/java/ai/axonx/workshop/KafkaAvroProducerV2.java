package ai.axonx.workshop;

//import ai.axonx.workshop.TweetLikes;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaAvroProducerV2 {

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

//        Producer<String, TweetLikes> producer = new KafkaProducer<String, TweetLikes>(properties);
//
//        String topic = "avro-tweet";
//
//        // copied from avro examples
//        TweetLikes tweetLikes = TweetLikes.newBuilder()
//                .setCreatedAt(12345678)
//                .setLiked(455)
//                .setText("tweet message")
//                .build();
//
//        ProducerRecord<String, TweetLikes> producerRecord = new ProducerRecord<String, TweetLikes>(
//                topic, tweetLikes
//        );
//
//        System.out.println(tweetLikes);
//        producer.send(producerRecord, new Callback() {
//            @Override
//            public void onCompletion(RecordMetadata metadata, Exception exception) {
//                if (exception == null) {
//                    System.out.println(metadata);
//                } else {
//                    exception.printStackTrace();
//                }
//            }
//        });
//
//        producer.flush();
//        producer.close();
    }
}
