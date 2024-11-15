package bg.sofia.uni.fmi.mjt.eventbus.subscribers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;

public class DeferredEventSubscriber<T extends Event<?>> implements Subscriber<T>, Iterable<T> {

    private final List<T> events = new ArrayList<>();

    /**
     * Store an event for processing at a later time.
     *
     * @param event the event to be processed
     * @throws IllegalArgumentException if the event is null
     */
    @Override
    public void onEvent(T event) {
        if (event == null) {
            throw new IllegalArgumentException("The event is null!");
        }
        events.add(event);
    }

    /**
     * Get an iterator for the unprocessed events. The iterator should provide the events sorted by
     * their priority in descending order. Events with equal priority are ordered in ascending order
     * of their timestamps.
     *
     * @return an iterator for the unprocessed events
     */
    @Override
    public Iterator<T> iterator() {
        List<T> sortedEvents = new ArrayList<>(events);

        sortedEvents.sort(new Comparator<T>() {
            @Override
            public int compare(T event1, T event2) {
                int priorityComparison = Integer.compare(event2.getPriority(), event1.getPriority());
                if (priorityComparison != 0) {
                    return priorityComparison;
                }

                return event1.getTimestamp().compareTo(event2.getTimestamp());
            }
        });

        return sortedEvents.iterator();
    }

    /**
     * Check if there are unprocessed events.
     *
     * @return true if there are unprocessed events, false otherwise
     */
    public boolean isEmpty() {
        return events.isEmpty();
    }

}