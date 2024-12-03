import java.util.Set;

public class IntSum {

    public static void main(String... args) {
        Set<Integer> numbers = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        int sumOf5 = numbers
            .stream()
            .limit(5)
            .mapToInt(Integer::intValue)
            .sum();


        //Always different result bc we are using set
        System.out.println(sumOf5);
    }

}