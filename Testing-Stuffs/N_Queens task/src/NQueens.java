import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NQueens {
    private int[][] board;
    private List<int[]> queenPositions;
    private int n;

    public NQueens(int n) {
        this.n = n;
        this.board = new int[n][n];
        this.queenPositions = new ArrayList<>();
        initializeBoard();
    }

    // Инициализира дъската със случайно разположени царици
    private void initializeBoard() {
        Random rand = new Random();
        for (int row = 0; row < n; row++) {
            int col = rand.nextInt(n); // Случайна колона
            board[row][col] = 1; // Поставя царица на случайно място
            queenPositions.add(new int[]{row, col});
        }
    }

    // Проверява дали всички царици са безопасни
    public boolean allQueensSafe() {
        for (int[] pos : queenPositions) {
            if (isUnderAttack(pos)) {
                return false;
            }
        }
        return true;
    }

    // Проверява дали дадена царица е под атака по колона
    private boolean isAttackedByColumn(int[] pos) {
        for (int[] queen : queenPositions) {
            if (pos[1] == queen[1] && !samePosition(pos, queen)) {
                return true;
            }
        }
        return false;
    }

    // Проверява дали дадена царица е под атака по ред
    private boolean isAttackedByRow(int[] pos) {
        for (int[] queen : queenPositions) {
            if (pos[0] == queen[0] && !samePosition(pos, queen)) {
                return true;
            }
        }
        return false;
    }

    // Проверява дали дадена царица е под атака по диагонал
    private boolean isAttackedByDiagonal(int[] pos) {
        for (int[] queen : queenPositions) {
            if (Math.abs(queen[0] - pos[0]) == Math.abs(queen[1] - pos[1]) && !samePosition(pos, queen)) {
                return true;
            }
        }
        return false;
    }

    // Проверява дали позицията е под атака
    private boolean isUnderAttack(int[] pos) {
        return isAttackedByColumn(pos) || isAttackedByRow(pos) || isAttackedByDiagonal(pos);
    }

    // Брои конфликтите за конкретна царица на дадена позиция
    public int countConflicts(int[] pos) {
        int conflicts = 0;
        for (int[] queen : queenPositions) {
            if (Math.abs(queen[0] - pos[0]) == Math.abs(queen[1] - pos[1]) && !samePosition(pos, queen)) {
                conflicts++;
            }
            if (pos[0] == queen[0] && !samePosition(pos, queen)) {
                conflicts++;
            }
            if (pos[1] == queen[1] && !samePosition(pos, queen)) {
                conflicts++;
            }
        }
        return conflicts;
    }

    // Връща случайна царица от позициите
    public int[] pickRandomQueen() {
        Random rand = new Random();
        return queenPositions.get(rand.nextInt(queenPositions.size()));
    }

    // Отпечатва всички позиции на цариците
    public void printBoard() {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    // Премества царицата от една позиция на друга
    public void moveQueen(int[] startPos, int[] endPos) {
        board[startPos[0]][startPos[1]] = 0;
        board[endPos[0]][endPos[1]] = 1;
        queenPositions.removeIf(pos -> samePosition(pos, startPos));
        queenPositions.add(endPos);
    }

    // Връща всички възможни позиции, където царицата може да се премести в същия ред
    public List<int[]> availablePositions(int[] pos) {
        List<int[]> availablePositions = new ArrayList<>();
        for (int col = 0; col < n; col++) {
            availablePositions.add(new int[]{pos[0], col});
        }
        return availablePositions;
    }

    // Проверява дали две позиции са еднакви
    private boolean samePosition(int[] pos1, int[] pos2) {
        return pos1[0] == pos2[0] && pos1[1] == pos2[1];
    }

    // Главен метод за стартиране на решаването на задачата
    public static void main(String[] args) {
        int n = 8; // Размер на дъската (например 8x8 за 8 царици)
        long startTime = System.currentTimeMillis();

        NQueens nQueens = new NQueens(n);
        int timer = 0;

        // Докато всички царици не са безопасни, се изпълнява алгоритъмът Min-Conflicts
        while (!nQueens.allQueensSafe()) {
            int minAttacks = n + 1; // Настройка на минимален брой атаки, който е по-голям от всички възможни конфликти
            int[] pickedQueen = nQueens.pickRandomQueen(); // Избиране на случайна царица

            // Намиране на всички възможни позиции за местене на избраната царица
            List<int[]> positions = nQueens.availablePositions(pickedQueen);
            int[] minConflictPosition = new int[]{-1, -1};

            for (int[] pos : positions) {
                nQueens.moveQueen(pickedQueen, pos); // Местим царицата временно
                int newNumberOfConflicts = nQueens.countConflicts(pos); // Броим конфликтите на новата позиция

                // Ако новият брой конфликти е по-малък, запазваме тази позиция като най-добра
                if (newNumberOfConflicts < minAttacks) {
                    minConflictPosition = pos;
                    minAttacks = newNumberOfConflicts;
                }
                nQueens.moveQueen(pos, pickedQueen); // Връщаме царицата на старото място
            }

            // Преместваме царицата на позицията с най-малко конфликти
            nQueens.moveQueen(pickedQueen, minConflictPosition);
            timer++; // Брояч на итерациите
        }

        // Отпечатваме крайния резултат
        System.out.println("Решение намерено след " + timer + " стъпки:");
        nQueens.printBoard();

        long endTime = System.currentTimeMillis();
        System.out.println("Време за изпълнение: " + (double)((endTime - startTime)/1000) );
    }
}
