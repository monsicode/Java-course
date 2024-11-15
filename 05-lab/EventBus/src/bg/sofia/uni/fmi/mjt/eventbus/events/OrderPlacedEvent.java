package bg.sofia.uni.fmi.mjt.eventbus.events;

import java.time.Instant;

public class OrderPlacedEvent extends AbstractEvent<OrderPayload> {

    // Конструктор, който използва конструктора на абстрактния клас
    public OrderPlacedEvent(Instant timestamp, int priority, String source, OrderPayload payload) {
        super(timestamp, priority, source, payload);
    }

    // Може да добавите допълнителни методи или логика, специфична за събитието "OrderPlaced"
}
