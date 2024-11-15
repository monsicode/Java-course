package bg.sofia.uni.fmi.mjt.eventbus.subscribers;

import bg.sofia.uni.fmi.mjt.eventbus.events.NotificationSentEvent;

public class NotificationSentSubscriber implements Subscriber<NotificationSentEvent> {

    @Override
    public void onEvent(NotificationSentEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null!");
        }

        // Обработваме събитието - тук например ще принтираме информация за събитието
        System.out.println("Notification Sent: ");
        System.out.println("Source: " + event.getSource());
        System.out.println("Priority: " + event.getPriority());
        System.out.println("Timestamp: " + event.getTimestamp());
        System.out.println("Payload: " + event.getPayload());
    }
}
