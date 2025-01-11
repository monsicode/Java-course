package bg.sofia.uni.fmi.mjt.poll.server.command;

import java.util.Arrays;

public class CommandCreator {

    public static Command newCommand(String input) {
        String[] parts = input.split("\\s+");

        String command = parts[0];
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);

        return new Command(command, args);
    }
}
