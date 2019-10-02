package ai.axonx.workshop;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class TwitterPostReader implements MessageBodyReader<TwitterPost> {
    @Override
    public boolean isReadable(Class<?> type, Type genericType,
                              Annotation[] annotations, MediaType mediaType) {
        return (type == TwitterPost.class);
    }

    @Override
    public TwitterPost readFrom(Class<TwitterPost> type, Type genericType,
                                Annotation[] annotations, MediaType mediaType,
                                MultivaluedMap<String, String> httpHeaders,
                                InputStream inputStream)
            throws IOException, WebApplicationException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        TwitterPost twitterPost = objectMapper.readValue(inputStream, TwitterPost.class);

        twitterPost.setQuotes();
        return twitterPost;
    }
}
