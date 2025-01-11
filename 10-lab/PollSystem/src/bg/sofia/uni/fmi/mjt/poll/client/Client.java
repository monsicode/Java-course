package bg.sofia.uni.fmi.mjt.poll.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT)) {
            System.out.println("Connected to server");

            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            Scanner scanner = new Scanner(System.in);
            String command;

            while (true) {
                System.out.print("Enter command (or 'disconnect' to quit): ");
                command = scanner.nextLine();

                if ("disconnect".equalsIgnoreCase(command)) {
                    System.out.println("Exiting client...");
                    break;
                }

                outputStream.write((command + "\n").getBytes());
                outputStream.flush();

                byte[] buffer = new byte[1024];
                int bytesRead = inputStream.read(buffer);
                String serverResponse = new String(buffer, 0, bytesRead);
                System.out.println("Server response: " + serverResponse);
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
