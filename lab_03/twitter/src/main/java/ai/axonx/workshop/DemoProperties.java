package ai.axonx.workshop;

import java.util.Properties;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;

/**
 *
 * @author Démian Janssen
 */
public class DemoProperties {

    private static final Properties properties = new Properties();

    public static Properties getProperties(String brokers) {
        properties.put("bootstrap.servers", brokers);
        properties.put("group.id", "demonstration");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", "30000");

        // Serializers
        // KafkaAvroSerializer -- KafkaAvroDeserializer
        String serializer = StringSerializer.class.getName();
        String deserializer = StringDeserializer.class.getName();
        properties.put("key.deserializer", deserializer);
        properties.put("value.deserializer", deserializer);
        properties.put("key.serializer", serializer);
        properties.put("value.serializer", serializer);
//        properties.put("schema.registry.url", "");
//        properties.put("specific.avro.reader", "true");
        return properties;
    }
}
