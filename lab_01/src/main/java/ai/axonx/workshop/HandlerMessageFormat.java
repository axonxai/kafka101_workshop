package ai.axonx.workshop;

public class HandlerMessageFormat {

    private final long id;
    private final String message;

    public HandlerMessageFormat(long id, String message) {
        this.id = id;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
