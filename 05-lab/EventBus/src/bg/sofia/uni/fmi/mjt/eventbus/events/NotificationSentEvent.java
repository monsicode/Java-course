package bg.sofia.uni.fmi.mjt.eventbus.events;

import java.time.Instant;

public class NotificationSentEvent extends AbstractEvent<NotificationPayload> {

    public NotificationSentEvent(Instant timestamp, int priority, String source, NotificationPayload payload) {
        super(timestamp, priority, source, payload);
    }

    @Override
    public String toString() {
        return "NotificationSentEvent{" +
            "timestamp=" + getTimestamp() +
            ", priority=" + getPriority() +
            ", source='" + getSource() + '\'' +
            ", payload=" + getPayload() +
            '}';
    }
}
