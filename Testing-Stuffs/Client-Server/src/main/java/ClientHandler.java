import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received: " + message);

                // Проверка за празно съобщение
                if (message.trim().isEmpty()) {
                    out.println("ERROR: Empty command.");
                    continue;
                }

                String[] parts = message.split(" ");
                if (parts.length < 1) {
                    out.println("ERROR: Invalid command format.");
                    continue;
                }

                int command;
                try {
                    command = Integer.parseInt(parts[0]);
                } catch (NumberFormatException e) {
                    out.println("ERROR: Invalid command type.");
                    continue;
                }

                switch (command) {
                    case 1: // CREATE_ORDER
                        if (parts.length < 2) {
                            out.println("ERROR: Missing username for CREATE_ORDER.");
                            continue;
                        }
                        createOrder(parts[1]);
                        out.println("SUCCESS: Order created for user " + parts[1]);
                        break;

                    case 2: // ADD_PRODUCT
                        if (parts.length < 4) {
                            out.println("ERROR: Invalid format for ADD_PRODUCT.");
                            continue;
                        }
                        try {
                            String username = parts[1];
                            String productKey = parts[2];
                            int quantity = Integer.parseInt(parts[3]);
                            addProduct(username, productKey, quantity);
                            out.println("SUCCESS: Product added to order.");
                        } catch (NumberFormatException e) {
                            out.println("ERROR: Quantity must be a number.");
                        }
                        break;

                    case 3: // FINALIZE_ORDER
                        if (parts.length < 2) {
                            out.println("ERROR: Missing username for FINALIZE_ORDER.");
                            continue;
                        }
                        finalizeOrder(parts[1]);
                        out.println("SUCCESS: Order finalized.");
                        break;

                    default:
                        out.println("ERROR: Unknown command.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void createOrder(String username) {
        System.out.println("Logic to creat new order");
//        Order newOrder = new Order(username);
//        orders.put(username, newOrder);
    }

    // Метод за добавяне на продукт към поръчка
    private void addProduct(String username, String productKey, int quantity) {
        System.out.println("Logic to add product");
    }

    // Метод за финализиране на поръчка
    private void finalizeOrder(String username) {
//        Order order = orders.get(username);
//        if (order != null) {
//            order.finalizeOrder();
//        }
        System.out.println("finilizing order");
    }
}
