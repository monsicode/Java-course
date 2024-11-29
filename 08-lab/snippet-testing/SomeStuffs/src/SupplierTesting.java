import java.util.function.Supplier;

public class SupplierTesting {

    public static void main(String[] args) {

        Supplier<String> func = () -> "\nHello world";

        System.out.println(func.get());


    }

}
