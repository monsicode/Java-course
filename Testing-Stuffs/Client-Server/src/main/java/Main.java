import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        // Стартиране на сървъра в нова нишка
        new Thread(() -> {
            Server server = new Server(12345);
            server.start();
        }).start();

        // Стартиране на клиента
        Client client = new Client("localhost", 12345);
        client.start();
    }

}
