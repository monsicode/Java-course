package bg.sofia.uni.fmi.mjt.poll.server.repository;

import bg.sofia.uni.fmi.mjt.poll.server.model.Poll;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InMemoryPollRepository implements PollRepository {

    private final Map<Integer, Poll> polls;

    public InMemoryPollRepository() {
        polls = new HashMap<>();
    }

    @Override
    public int addPoll(Poll poll) {
        nullCheck(poll, "Cannot add null poll");
        polls.putIfAbsent(polls.size() + 1, poll);
        return polls.size();
    }

    @Override
    public Poll getPoll(int pollId) {
        nullCheck(pollId, "Cannot have null id for poll");
        return polls.get(pollId);
    }

    @Override
    public Map<Integer, Poll> getAllPolls() {
        return Collections.unmodifiableMap(polls);
    }

    @Override
    public void clearAllPolls() {
        polls.clear();
    }

    private <T> void nullCheck(T obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

}