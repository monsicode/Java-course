package bg.sofia.uni.fmi.mjt.eventbus;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.exception.MissingSubscriptionException;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.DeferredEventSubscriber;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.Subscriber;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBusImpl implements EventBus {

    private final Map<Class<? extends Event<?>>, List<Subscriber<?>>> eventHandlers = new HashMap<>();

    EventBusImpl() {
    }

    @Override
    public <T extends Event<?>> void subscribe(Class<T> eventType, Subscriber<? super T> subscriber) {

        if (!eventHandlers.containsKey(eventType)) {
            eventHandlers.put(eventType, new ArrayList<>());
        }

        eventHandlers.get(eventType).add(subscriber);

    }

    @Override
    public <T extends Event<?>> void unsubscribe(Class<T> eventType, Subscriber<? super T> subscriber)
        throws MissingSubscriptionException {
        if (eventType == null || subscriber == null) {
            throw new IllegalArgumentException("Subscriber or event type is null!");
        }

        List<Subscriber<?>> handlers = eventHandlers.get(eventType);
        if (handlers != null && handlers.contains(subscriber)) {
            handlers.remove(subscriber);
        } else {
            throw new MissingSubscriptionException("The subscriber is not subscribed to the event");
        }
    }

    @Override
    public <T extends Event<?>> void publish(T event) {
        if (event == null) {
            throw new IllegalArgumentException("Event is null!");
        }

        List<Subscriber<?>> handlers = eventHandlers.getOrDefault(event.getClass(), Collections.emptyList());

        for (Subscriber<?> subscriber : handlers) {
            if (subscriber instanceof DeferredEventSubscriber) {
                DeferredEventSubscriber<T> deferredSubscriber = (DeferredEventSubscriber<T>) subscriber;
                deferredSubscriber.onEvent(event);
            } else {
                Subscriber<T> typedSubscriber = (Subscriber<T>) subscriber;
                typedSubscriber.onEvent(event);
            }
        }
    }

    @Override
    public void clear() {
        eventHandlers.clear();
    }

    @Override
    public Collection<? extends Event<?>> getEventLogs(Class<? extends Event<?>> eventType, Instant from, Instant to) {
        if (eventType == null) {
            throw new IllegalArgumentException("Event type is null!");
        }
        if (from == null) {
            throw new IllegalArgumentException("Event type is null!");
        }
        if (to == null) {
            throw new IllegalArgumentException("Event type is null!");
        }

        List<Subscriber<?>> subscribers = eventHandlers.getOrDefault(eventType, Collections.emptyList());
        List<Event<?>> eventLogs = new ArrayList<>();

        for (Subscriber<?> subscriber : subscribers) {
            if (subscriber instanceof DeferredEventSubscriber) {
                DeferredEventSubscriber<?> deferredSubscriber = (DeferredEventSubscriber<?>) subscriber;

                for (Event<?> event : deferredSubscriber) {
                    if (event.getTimestamp().compareTo(from) >= 0 && event.getTimestamp().compareTo(to) < 0) {
                        eventLogs.add(event);
                    }
                }
            }
        }

        return Collections.unmodifiableCollection(eventLogs);

    }

}
