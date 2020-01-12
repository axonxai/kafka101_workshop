package ai.axonx.workshop;

import com.google.gson.Gson;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Démian Janssen
 */
public class Demonstration {

    /*
     * Gson voor een fijne integratie van JSON naar een KafkaProducer om het 
     * vervolgens met AVRO te serializen.
     * 
     * toJsonTree voor conversie naar JSON formaat,
     * fromJson voor conversie van JSON formaat
     *
     * Waarom JSON als je AVRO serialization hebt? 
     * Het kan dat een andere service JSON berichten naar jou verstuurd, 
     * als je die automagisch kan parsen, scheelt dat wat kopfschmerzen in je 
     * doorgeefluik.
     */
    public static void GsonDemonstration() {
        Gson gson = new Gson();
        System.out.println("JSON representation of a new object");
        // Om een syntactisch correcte JSON representatie te krijgen van je Java object
        System.out.println(gson.toJsonTree(new AvroDemo()));

        String perfect = "{\"key\":\"example-key\",\"value\":\"example-value\"}";
        AvroDemo fromJsonPerfect = gson.fromJson(perfect, AvroDemo.class);
        System.out.println("Parsed from JSON");
        // Wanneer de JSON representatie perfect past op je AVRO-gegenereerde class
        System.out.println(fromJsonPerfect);

        String teveel = "{\"key\":\"example-key\",\"value\":\"example-value\",\"example-list\":[123,true]}";
        AvroDemo fromJsonTeveel = gson.fromJson(teveel, AvroDemo.class);
        System.out.println("Parsed from JSON with extra fields");
        // Teveel velden in je JSON representatie? Geen probleem, die worden genegeerd
        System.out.println(fromJsonTeveel);

        String incompleet = "{}";
        AvroDemo fromJsonIncompleet = gson.fromJson(incompleet, AvroDemo.class);
        System.out.println("Parsed from JSON without fields");
        // Missende waarden? Dat kan wel een probleem worden, in dit geval worden ze geinitialiseerd met null
        System.out.println(fromJsonIncompleet);
    }

    public static void main(String[] args) {
        // Topics -- Twitter zoektermen
        List<String> topics = Arrays.asList("obama", "hillary", "pocahontas", "poor");
        String brokers = "localhost:9092";

        TwitterProducer.init(topics,brokers);
    }
}
