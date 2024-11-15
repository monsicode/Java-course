package bg.sofia.uni.fmi.mjt.eventbus;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.exception.MissingSubscriptionException;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.Subscriber;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class EventBusImpl implements EventBus {

    private final Map<Class<? extends Event<?>>, Set<Subscriber<?>>> eventHandlers = new HashMap<>();
    private final Map<Class<? extends Event<?>>, Queue<Event<?>>> eventLogs = new HashMap<>();

    EventBusImpl() {
    }

    @Override
    public <T extends Event<?>> void subscribe(Class<T> eventType, Subscriber<? super T> subscriber) {
        if (eventType == null) {
            throw new IllegalArgumentException("Event type can't be null!");
        }
        if (subscriber == null) {
            throw new IllegalArgumentException("Subscriber can't be null!");
        }

        eventHandlers.putIfAbsent(eventType, new HashSet<>());
        eventHandlers.get(eventType).add(subscriber);

    }

    @Override
    public <T extends Event<?>> void unsubscribe(Class<T> eventType, Subscriber<? super T> subscriber)
        throws MissingSubscriptionException {
        if (eventType == null || subscriber == null) {
            throw new IllegalArgumentException("Subscriber or event type is null!");
        }

        //getOrDefault(), so i don't check for null values
        Set<Subscriber<?>> handlers = eventHandlers.get(eventType);
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

        eventLogs.putIfAbsent((Class<T>) event.getClass(), new PriorityQueue<>(new Comparator<Event<?>>() {
            @Override
            public int compare(Event<?> o1, Event<?> o2) {
                return o1.getTimestamp().compareTo(o2.getTimestamp());
            }
        }));
        eventLogs.get((Class<T>) event.getClass()).add(event);

        Set<Subscriber<?>> handlers = eventHandlers.getOrDefault(event.getClass(), new HashSet<>());

        for (Subscriber<?> subscriber : handlers) {
            Subscriber<T> typedSubscriber = (Subscriber<T>) subscriber;
            typedSubscriber.onEvent(event);
        }
    }

    @Override
    public void clear() {
        eventHandlers.clear();
        eventLogs.clear();
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
        if (from.equals(to)) {
            return Collections.emptyList();
        }
        Queue<Event<?>> result = new PriorityQueue<>(new Comparator<Event<?>>() {
            @Override
            public int compare(Event<?> o1, Event<?> o2) {
                return o1.getTimestamp().compareTo(o2.getTimestamp());
            }
        });

        Queue<Event<?>> events = eventLogs.getOrDefault(eventType, new PriorityQueue<>());

        for (Event<?> event : events) {
            if (event.getTimestamp().compareTo(from) >= 0 && event.getTimestamp().compareTo(to) < 0) {
                result.add(event);
            }
        }

        return Collections.unmodifiableCollection(result);
    }

    @Override
    public <T extends Event<?>> Collection<Subscriber<?>> getSubscribersForEvent(Class<T> eventType) {
        if (eventType == null) {
            throw new IllegalArgumentException("Event type can't be null");
        }

        return Collections.unmodifiableCollection(eventHandlers.getOrDefault(eventType, new HashSet<>()));
    }

}
