import java.util.ArrayList;
import java.util.List;

public class JumpFrogPuzzle {
    public static void printState(List<Character> frogs) {
        for (char frog : frogs) {
            System.out.print(frog + " ");
        }
        System.out.println();
    }

    public static void solveJumpFrogPuzzle(int n) {
        List<Character> frogs = new ArrayList<>();

        // Добавяне на жабите отляво ('L')
        for (int i = 0; i < n; i++) {
            frogs.add('L');
        }

        // Добавяне на празното място (' ')
        frogs.add(' ');

        // Добавяне на жабите отдясно ('R')
        for (int i = 0; i < n; i++) {
            frogs.add('R');
        }

        // Извеждане на началното състояние
        printState(frogs);

        int totalSteps = 2 * n + 1;  // Общият брой стъпки
        for (int step = 1; step <= totalSteps; step++) {
            if (step % 2 != 0) {
                System.out.println("Move from left to right.");
            } else {
                System.out.println("Move from right to left.");
            }
            printState(frogs);
        }
    }

    public static void main(String[] args) {
        int n = 3; // Брой жаби на всяка страна, може да се промени

        solveJumpFrogPuzzle(n);
    }
}
