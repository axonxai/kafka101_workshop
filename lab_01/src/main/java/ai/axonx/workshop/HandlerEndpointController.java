package ai.axonx.workshop;

        import org.springframework.web.bind.annotation.*;
        import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HandlerEndpointController {

    private static final String TEMPLATE = "message:%s";
    private final AtomicLong counter = new AtomicLong();
    private HandlerProducer handler;

    @RequestMapping(value = "/producer", method = RequestMethod.POST)
    public HandlerMessageFormat topic(@RequestBody String body) {

        handler = new HandlerProducer();
        handler.setEventString(body);
        Thread t = new Thread(handler);
        t.start();

        return new HandlerMessageFormat(counter.incrementAndGet(), String.format(TEMPLATE, body));
    }
}
