package bg.sofia.uni.fmi.mjt.eventbus.events;

import java.time.Instant;

public abstract class AbstractEvent<T extends Payload<?>> implements Event<T> {

    private final Instant timestamp;
    private final int priority;
    private final String source;
    private final T payload;

    // Конструктор за инициализиране на основните полета
    protected AbstractEvent(Instant timestamp, int priority, String source, T payload) {
        if (timestamp == null || source == null || payload == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        this.timestamp = timestamp;
        this.priority = priority;
        this.source = source;
        this.payload = payload;
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public T getPayload() {
        return payload;
    }
}
