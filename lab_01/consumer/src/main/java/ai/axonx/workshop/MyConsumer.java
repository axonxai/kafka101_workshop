package ai.axonx.workshop;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;


public class MyConsumer {

    public static void main(String[] args) {
        String topic = "workshop";

        String serializer = StringSerializer.class.getName();
        String deserializer = StringDeserializer.class.getName();

        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("acks", "1");
        props.put("group.id", "workshop");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "200");
        props.put("auto.offset.reset", "earliest");


        props.put("key.deserializer", deserializer);
        props.put("value.deserializer", deserializer);
        props.put("key.serializer", serializer);
        props.put("value.serializer", serializer);


        // TODO: Instantieer een KafkaConsumer
        KafkaConsumer<String, String> consumer;

        // TODO: Subscribe naar een topic

        while (true) {
            // TODO: Print de berichten naar stdout
        }
    }
}
