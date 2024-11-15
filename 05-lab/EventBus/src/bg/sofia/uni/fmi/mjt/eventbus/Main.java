package bg.sofia.uni.fmi.mjt.eventbus;

import bg.sofia.uni.fmi.mjt.eventbus.events.NotificationSentEvent;
import bg.sofia.uni.fmi.mjt.eventbus.events.NotificationPayload;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.NotificationSentSubscriber;

import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        System.out.println("Wellcome to event-driven app!");
        //Създаваме екземпляри на събитията и абонатите
        NotificationSentEvent notificationSentEvent = new NotificationSentEvent(
            Instant.now(), 1, "OrderService", new NotificationPayload("Your order is placed!", "monkata")
        );

        NotificationSentSubscriber notificationSentSubscriber = new NotificationSentSubscriber();

        // Абонираме Subscriber за събитие
        EventBusImpl eventBus = new EventBusImpl();
        eventBus.subscribe(NotificationSentEvent.class, notificationSentSubscriber);

        // Публикуваме събитие
        eventBus.publish(notificationSentEvent);

    }
}
