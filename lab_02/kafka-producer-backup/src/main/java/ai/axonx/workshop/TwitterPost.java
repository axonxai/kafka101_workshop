package ai.axonx.workshop;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.util.Pair;

import java.util.ArrayList;

public class TwitterPost {
    @JsonProperty("count")
    public int count;

    @JsonProperty("total")
    public int total;

    @JsonProperty("_embedded")
    public JsonNode _embedded;
    public ArrayList<Pair<String, String>> QuotesTags = new ArrayList<Pair<String, String>>();


    public void setQuotes() {
        for (JsonNode node : _embedded.elements().next()) {
            QuotesTags.add(new Pair(node.get("value").asText(), Replace(node.get("tags").toString())));
            //System.out.println(Replace(node.get("tags").toString()));
        }
    }

    private String Replace(String string) {
        String result = "";
        for (char c : string.toCharArray()) {
            if (c != '"' && c != '[' && c != ']')
                result += c;
        }
        return result;
    }
}
