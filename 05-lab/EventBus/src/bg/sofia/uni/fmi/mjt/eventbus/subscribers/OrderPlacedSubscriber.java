package bg.sofia.uni.fmi.mjt.eventbus.subscribers;

import bg.sofia.uni.fmi.mjt.eventbus.events.OrderPlacedEvent;

public class OrderPlacedSubscriber implements Subscriber<OrderPlacedEvent> {

    @Override
    public void onEvent(OrderPlacedEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null!");
        }

        // Обработваме събитието - тук например ще принтираме информация за събитието
        System.out.println("Order Placed: ");
        System.out.println("Order ID: " + event.getPayload().getOrderId());
        System.out.println("Order Time: " + event.getTimestamp());
        System.out.println("Priority: " + event.getPriority());
    }
}
