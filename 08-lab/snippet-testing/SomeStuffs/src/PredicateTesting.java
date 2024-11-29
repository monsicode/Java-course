import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class PredicateTesting {

    public static void main(String[] args) {
        Predicate<Integer> biggerThanTen = x -> x > 10;
        Predicate<Integer> lessThanTwenty = x -> x < 20;

        //System.out.println(biggerThanTen.or(lessThanTwenty).test(22));
        //System.out.println(biggerThanTen.negate().test(9));

        Predicate<String> isJohn = Predicate.isEqual("John");

        System.out.println(isJohn.test("John"));


    }

}
