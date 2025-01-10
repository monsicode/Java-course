package client;

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

            // Подготовка на потоците за комуникация
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            // Сканер за въвеждане на команди в конзолата
            Scanner scanner = new Scanner(System.in);
            String command;

            while (true) {
                // Четене на команда от конзолата
                System.out.print("Enter command (or 'exit' to quit): ");
                command = scanner.nextLine();

                // Излизане от приложението, ако въведеш 'exit'
                if ("exit".equalsIgnoreCase(command)) {
                    System.out.println("Exiting client...");
                    break;
                }

                // Изпращане на командата към сървъра
                outputStream.write((command + "\n").getBytes());
                outputStream.flush();

                // Четене на отговора от сървъра
                byte[] buffer = new byte[1024];
                int bytesRead = inputStream.read(buffer);
                String serverResponse = new String(buffer, 0, bytesRead);
                System.out.println("Server response: " + serverResponse);
            }

            // Затваряне на скенера и сокета
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
