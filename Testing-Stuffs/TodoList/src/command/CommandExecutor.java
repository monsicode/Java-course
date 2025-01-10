package command;

import storage.Storage;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommandExecutor {
    private static final String INVALID_ARGS_COUNT_MESSAGE_FORMAT =
        "Invalid count of arguments: \"%s\" expects %d arguments. Example: \"%s\"";

    private static final String ADD = "add-todo";
    private static final String COMPLETE = "complete-todo";
    private static final String LIST = "list";

    private static final String REGISTER = "register";
    private static final String LOGIN = "login";

    private final Set<String> registeredUsers = new HashSet<>();
    private final Map<SocketChannel, String> loggedUsers = new HashMap<>();

    private final Storage storage;

    public CommandExecutor(Storage storage) {
        this.storage = storage;
    }

    public String execute(Command cmd, SocketChannel client) {
        return switch (cmd.command()) {
            case ADD -> addToDo(cmd.arguments(), client);
            case COMPLETE -> complete(cmd.arguments(), client);
            case LIST -> list(client);
            case REGISTER -> register(cmd.arguments());
            case LOGIN -> login(cmd.arguments(), client);
            default -> "Unknown command";
        };
    }

    private String register(String[] args) {
        if (args.length != 1) {
            return "Usage: register <username>";
        }
        String username = args[0].trim();
        if (registeredUsers.contains(username)) {
            return "User already registered.";
        }
        registeredUsers.add(username);
        return "User " + username + " successfully registered.";
    }

    private String login(String[] args, SocketChannel client) {
        if (args.length != 1) {
            return "Usage: login <username>";
        }
        String username = args[0].trim();
        if (!registeredUsers.contains(username)) {
            return "User not registered.";
        }
        loggedUsers.put(client, username);
        return "User " + username + " successfully logged in.";
    }

    private String addToDo(String[] args, SocketChannel client) {
        String user = loggedUsers.get(client);
        if (user == null) {
            return "You need to log in first.";
        }

        if (args.length != 1) {
            return String.format(INVALID_ARGS_COUNT_MESSAGE_FORMAT, ADD, 1, ADD + " <todo_item>");
        }

        String todo = args[0];
        int todoID = storage.add(user, todo);
        return String.format("Added new To Do with ID %s for user %s", todoID, user);
    }

    private String complete(String[] args, SocketChannel client) {
        String user = loggedUsers.get(client);
        if (user == null) {
            return "You need to log in first.";
        }

        if (args.length != 1) {
            return String.format(INVALID_ARGS_COUNT_MESSAGE_FORMAT, COMPLETE, 1, COMPLETE + " <todo_item_id>");
        }

        int todoID;
        try {
            todoID = Integer.parseInt(args[0].trim());
        } catch (NumberFormatException e) {
            return "Invalid ID provided for command \"complete-todo\": only integer values are allowed.";
        }

        storage.remove(user, todoID);
        return String.format("Completed To Do with ID %s for user %s", todoID, user);
    }

    private String list(SocketChannel client) {
        String user = loggedUsers.get(client);
        if (user == null) {
            return "You need to log in first.";
        }

        var todos = storage.list(user);
        if (todos.isEmpty()) {
            return "No To-Do items found for user with name " + user;
        }

        StringBuilder response = new StringBuilder(String.format("To-Do list of user %s:%n", user));
        todos.forEach((k, v) -> response.append(String.format("[%d] %s%n", k, v)));
        return response.toString();
    }
}
