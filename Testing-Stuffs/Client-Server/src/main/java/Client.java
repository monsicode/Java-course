import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private final String serverAddress;
    private final int serverPort;

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void start() {
        try (Socket socket = new Socket(serverAddress, serverPort);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to server!");
            while (true) {
                System.out.println("Choose a command: ");
                System.out.println("1: CREATE_ORDER");
                System.out.println("2: ADD_PRODUCT");
                System.out.println("3: FINALIZE_ORDER");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Консумира newline символа

                String command = "";
                switch (choice) {
                    case 1:
                        System.out.println("Enter username for CREATE_ORDER:");
                        String username = scanner.nextLine();
                        command = "1 " + username;
                        break;
                    case 2:
                        System.out.println("Enter username, product key, and quantity for ADD_PRODUCT:");
                        String productInput = scanner.nextLine();
                        command = "2 " + productInput;
                        break;
                    case 3:
                        System.out.println("Enter username for FINALIZE_ORDER:");
                        String finalizeUser = scanner.nextLine();
                        command = "3 " + finalizeUser;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                        continue;
                }

                out.println(command); // Изпращаме командата към сървъра
                String response = in.readLine();
                System.out.println("Server response: " + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
