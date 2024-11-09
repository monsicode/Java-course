import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        Product product = new Product("Pizza Margherita", 12.99);

        try {
            objectMapper.writeValue(new File("product.json"), product);
            System.out.println("Обектът е записан в product.json");

            // Зареждаме обекта обратно от JSON файла
            Product loadedProduct = objectMapper.readValue(new File("product.json"), Product.class);
            System.out.println("Зареден обект: " + loadedProduct);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Вътрешен клас Product за тестови цели
    static class Product {
        private String name;
        private double price;

        // Празен конструктор, нужен за десериализацията
        public Product() {
        }

        // Конструктор с параметри
        public Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

        // Гетъри и сетъри
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        // toString метод за отпечатване на обекта
        @Override
        public String toString() {
            return "Product{name='" + name + "', price=" + price + '}';
        }
    }
}
