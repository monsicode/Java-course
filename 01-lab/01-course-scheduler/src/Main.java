import java.util.Arrays;

public class Main {

    public static void swapMatrix(int[][] courses, int x, int x1) {
        int[] temp = courses[x];
        courses[x] = courses[x1];
        courses[x1] = temp;
    }

    public static void sortFirst(int[][] courses) {
        for (int i = 0; i < courses.length; i++) {
            for (int j = 0; j < courses.length; j++) {
                if (courses[i][0] < courses[j][0]) {
                    swapMatrix(courses, i, j);
                }
            }
        }
    }

    public static int maxNonOverlappingCourses(int[][] courses) {
        sortFirst(courses);

        int count = 0;

        for (int i = 0; i < courses.length - 1; i++) {
            if (courses[i][1] <= courses[i + 1][0]) {
                count++;
            }
        }


        return count + 1;
    }

    public static void main(String[] args) {

        int[][] courses = {{9, 11}, {10, 12}, {11, 13}, {15, 16}};

        System.out.println(maxNonOverlappingCourses(courses));

        for (var x : courses) {
            System.out.print(Arrays.toString(x));
            System.out.println("");
        }
    }
}
