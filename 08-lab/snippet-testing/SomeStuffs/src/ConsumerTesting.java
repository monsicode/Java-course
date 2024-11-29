import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerTesting {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        Consumer<Integer> printNumber = (x) -> System.out.println("Number: " + x);
        Consumer<Integer> multiplyAndPrint = (x) -> System.out.println("Multiplied: " + (x * 2));

        Consumer<Integer> chainedConsumer = printNumber.andThen(multiplyAndPrint);
        numbers.forEach(chainedConsumer);


        Consumer<Integer> consumer = (x) -> System.out.println("hello" + x);
        Consumer<Integer> consumer2 = (x) -> System.out.println("hello2 " + x);

        consumer2.andThen(consumer).accept(20);
    }

}
