package bg.sofia.uni.fmi.mjt.poll.server.command;

import bg.sofia.uni.fmi.mjt.poll.server.model.Poll;
import bg.sofia.uni.fmi.mjt.poll.server.repository.PollRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandExecutor {

    protected PollRepository pollRepository;
    private static int pollCounter = 1;

    private static final int MIN_COUNT_ARGUMENTS = 3;

    public CommandExecutor(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    private static final String CREATE = "create-poll";
    private static final String SUBMIT = "submit-vote";
    private static final String LIST = "list-polls";

    public String execute(Command cmd) {
        return switch (cmd.command()) {
            case CREATE -> createPoll(cmd.args());
            case SUBMIT -> submitVote(cmd.args());
            case LIST -> listPoll(cmd.args());
            default -> "{\"status\":\"ERROR\",\"message\":\"Unknown command.\"}";
        };
    }

    public String createPoll(String[] args) {
        if (args.length < MIN_COUNT_ARGUMENTS) {
            return "{\"status\":\"ERROR\",\"message\":\"You must provide a question and at least two options.\"}";
        }

        String question = args[0].trim();

        if (!question.endsWith("?")) {
            return "{\"status\":\"ERROR\",\"message\":\"Usage: " +
                "create-poll <question> <option-1> <option-2> [... <option-N>]\"}";
        }

        List<String> optionsList =
            new ArrayList<>(Arrays.asList(Arrays.copyOfRange(args, 1, args.length)));

        optionsList.replaceAll(String::trim);

        Map<String, Integer> options = new HashMap<>();
        for (String option : optionsList) {
            if (!option.isBlank()) {
                options.put(option, 0);
            }
        }

        Poll newPoll = new Poll(question, options);
        pollRepository.addPoll(newPoll);

        return String.format("{\"status\":\"OK\",\"message\":\"Poll %d created successfully.\"}", pollCounter++);
    }

    public String listPoll(String[] args) {
        Map<Integer, Poll> polls = pollRepository.getAllPolls();
        if (polls.isEmpty()) {
            return "{\"status\":\"ERROR\",\"message\":\"No active polls available.\"}";
        }

        StringBuilder result = new StringBuilder("{\"status\":\"OK\",\"polls\":{");
        for (Map.Entry<Integer, Poll> entry : polls.entrySet()) {
            Integer pollId = entry.getKey();
            Poll poll = entry.getValue();

            result.append("\"").append(pollId).append("\":{")
                .append("\"question\":\"").append(poll.question()).append("\",")
                .append("\"options\":{");

            poll.options().forEach((option, votes) ->
                result.append("\"").append(option).append("\":").append(votes).append(",")
            );
            if (!poll.options().isEmpty()) {
                result.setLength(result.length() - 1);
            }
            result.append("}},");
        }
        if (!polls.isEmpty()) {
            result.setLength(result.length() - 1);
        }
        result.append("}}");
        return result.toString();
    }

    public String submitVote(String[] args) {
        try {
            int pollId = Integer.parseInt(args[0]);

            String chosenOption = args[1];

            Poll poll = pollRepository.getPoll(pollId);

            if (poll != null) {
                chosenOption = chosenOption.trim().replaceAll("[\\r\\n]+", "").replaceAll("\\s+", " ");
                if (poll.options().containsKey(chosenOption)) {
                    poll.options().put(chosenOption, poll.options().get(chosenOption) + 1);
                    return String.format(
                        "{\"status\":\"OK\",\"message\":\"Vote submitted successfully for option: %s\"}",
                        chosenOption);
                } else {
                    return String.format(
                        "{\"status\":\"ERROR\",\"message\":\"Invalid option. Option %s does not exist.\"}",
                        chosenOption);
                }

            } else {
                return String.format("{\"status\":\"ERROR\",\"message\":\"Poll with ID %d does not exist.\"}", pollId);
            }
        } catch (NumberFormatException e) {
            return "{\"status\":\"ERROR\",\"message\":\"Poll ID must be an integer.\"}";
        }
    }

}
