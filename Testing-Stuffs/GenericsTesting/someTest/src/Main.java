import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.SequencedCollection;
import java.util.Vector;

public class Main {

    public static <T> void fun(Box<T> b1, Box<T> b2) {
        System.out.println("Okey");
    }

    public static void addNumber(List<? super Integer> list) {
        System.out.println("add number is okey");
    }

    public static void main(String[] args) {
        Box<String> box1 = new Box<>();
        Box<String> box2 = new Box<>();
        fun(box1, box2);

        List<Number> list = new ArrayList<>();
        addNumber(list);

        Object obj = new Object();

        obj.


    }


}
