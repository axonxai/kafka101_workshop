package ai.axonx.workshop;

        import org.springframework.web.bind.annotation.*;
        import java.util.concurrent.atomic.AtomicLong;

@RestController
public class EndpointController {

    private Producer handler;

    @RequestMapping(value = "/producer", method = RequestMethod.POST)
    String startProducer(@RequestBody String body) {

        handler = new Producer();
        handler.setEventString(body);
        Thread t = new Thread(handler);
        t.start();

        return "Message received: " + body;
    }
}
