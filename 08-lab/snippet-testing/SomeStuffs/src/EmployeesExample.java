import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class EmployeesExample {

    public static void main(String... args) {
        Employee e1 = new Employee(1, "Бай Кольо", 56, 1200.00, 340.00);
        Employee e2 = new Employee(2, "Шефа", 43, 15000.00, 30000.00);
        Employee e3 = new Employee(3, "Стажанта", 20, 850.00, 0.00);
//
//        List<Employee> employees = List.of(e1, e2, e3);
//
//        System.out.println(getEmployeeSalariesTotal(employees));

        //rev order
        List<Integer> numbers =
            Stream.of(1, 4, 3, 2, 5, 4).filter(val -> val < 4).sorted((n1, n2) -> n2.compareTo(n1)).toList();
        List<Integer> numbers3 =
            Stream.of(1, 4, 3, 2, 5, 4).filter(val -> val < 4).sorted(Comparator.reverseOrder()).toList();
        List<Integer> numbers4 =
            Stream.of(1, 4, 3, 2, 5, 4).filter(val -> val < 4).sorted((n1, n2) -> Integer.compare(n2, n1)).toList();
        List<Integer> numbers6 =
            Stream.of(1, 4, 3, 2, 5, 4).filter(val -> val < 4).sorted(Comparator.comparingInt(n -> -n)).toList();

        List<Integer> numbers2 = Stream.of(1, 4, 3, 2, 5, 4).filter(val -> val < 4).sorted().toList();
        List<Integer> numbers5 = Stream.of(1, 4, 3, 2, 5, 4).filter(val -> val < 4).sorted(Integer::compare).toList();

        //System.out.println(numbers6);

        List<Employee> empl = Stream.of(e1, e2, e3)
            .filter(e -> e.getSalary() > 900)
            .toList();

        //System.out.println(empl.stream().map(Employee::getName).toList());

    }


    public static double getEmployeeSalariesTotal(List<Employee> employees) {
        // Stream<T> -> reduce -> V
        //
        // initial value (identity): the initial value of the reduction and the default result if the stream is empty
        // accumulator: This is a BinaryOperator function that takes two parameters:
        //              a partial result of the reduction operation and the next element of the stream
        // combiner: combine the results of the accumulator function when processing elements in parallel
        //           The combiner is necessary in parallel operations, where the input stream is divided into
        //           multiple sub-streams that are processed independently and then the partial results
        //           are combined into a final result.
        return employees.parallelStream()
            .reduce(
                0.0,                       // initial value
                (res, el) -> res + el.getSalary(), // accumulator
                (left, right) -> left + right);    // combiner
    }
}