import command.CommandExecutor;
import storage.InMemoryStorage;
import server.Server;
import storage.Storage;

public class Main {
    public static void main(String[] args) {
        Storage storage = new InMemoryStorage();
        CommandExecutor commandExecutor = new CommandExecutor(storage);

        int port = 8080;
        Server server = new Server(port, commandExecutor);
        server.start();
    }
}
