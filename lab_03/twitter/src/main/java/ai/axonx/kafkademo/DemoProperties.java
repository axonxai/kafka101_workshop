package ai.axonx.kafkademo;

import java.util.Properties;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 *
 * @author Démian Janssen
 */
public class DemoProperties {

    private static final Properties properties = new Properties();

    public static Properties getProperties(String brokers) {
        String serializer = StringSerializer.class.getName();
        String deserializer = StringDeserializer.class.getName();

        properties.put("bootstrap.servers", brokers);
        properties.put("group.id", "demonstration");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", "30000");

        // Serializers
        properties.put("key.deserializer", deserializer);
        properties.put("value.deserializer", deserializer);
        properties.put("key.serializer", serializer);
        properties.put("value.serializer", serializer);

        return properties;
    }
}
