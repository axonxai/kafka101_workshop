package ai.axonx.workshop;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 *  Producer Sends message to Kafka topic
 *
 */
public class HandlerProducer implements Runnable {

    private String eventString;
    private final Producer<String, String> producer;

    public HandlerProducer() {
        Properties properties = new Properties();

        // normal producer
//        properties.loadFromXML(new FileInputStream("properties.xml"));
        properties.setProperty("bootstrap.servers", System.getenv().getOrDefault("KAFKA_BROKER_LIST", "127.0.0.1:29092"));
        properties.setProperty("acks", "all");
        properties.setProperty("retries", "10");

        // avro part
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        properties.setProperty("schema.registry.url", System.getenv().getOrDefault("SCHEMA_REGISTRY_URL", "http://127.0.0.1:8081"));

        producer = new KafkaProducer<>(properties);
    }

    public void setEventString(String eventString) {
        this.eventString = eventString;
    }

    public void sendEvent() {
        String topic = System.getenv().getOrDefault("PRODUCE_TOPIC", "hello_world");
        String key = null;
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, eventString);

        producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception == null) {
                    System.out.println("topic offset " + metadata.offset() + " --- message: " + eventString);
                } else {
                    exception.printStackTrace();
                }
            }
        });

        producer.flush();
        producer.close();
    }

    @Override
    public void run() {
        sendEvent();
    }
}
