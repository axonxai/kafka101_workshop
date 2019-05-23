package ai.axonx.workshop;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Date;
import java.util.Properties;


/**
 * MyProducer Sends message to Kafka topic
 */
public class MyProducer {


    public static void main(String[] args) throws InterruptedException {

        String topic = "workshop";
        final int sleepSeconds = 1;

        String serializer = StringSerializer.class.getName();
        String deserializer = StringDeserializer.class.getName();

        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("acks", "1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "100");
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "3000");

        props.put("key.deserializer", deserializer);
        props.put("value.deserializer", deserializer);
        props.put("key.serializer", serializer);
        props.put("value.serializer", serializer);


        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        while (true) {
            String message = new Date().toString();
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic,"msg " + message);
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println("topic offset " + metadata.offset());
                    } else {
                        exception.printStackTrace();
                    }
                }
            });
            Thread.sleep(sleepSeconds * 1000);
        }
    }
}
