import java.util.Arrays;

public class CourseScheduler {

    public static void swapMatrix(int[][] courses, int x, int x1) {
        int[] temp = courses[x];
        courses[x] = courses[x1];
        courses[x1] = temp;
    }

    public static void sortFirst(int[][] courses) {
        for (int i = 0; i < courses.length; i++) {
            for (int j = 0; j < courses.length; j++) {
                if (courses[i][1] < courses[j][1]) {
                    swapMatrix(courses, i, j);
                }
//                else if(courses[i][0] == courses[j][0] && courses[i][1] < courses[j][1])
//                {
//                    swapMatrix(courses, i, j);
//                }
            }
        }
    }

    public static int maxNonOverlappingCourses(int[][] courses) {

        if(courses.length == 0){
            return 0;
        }

        sortFirst(courses);

        int visited = 0;
        int count = 0;

        for (int i = 0; i < courses.length - 1; i++) {
            if (courses[visited][1] <= courses[i + 1][0]) {
                visited=i+1;
                count++;
            }
        }

        return count + 1;
    }


    public static void main(String[] args) {

        int[][] courses = {{0, 24}, {1, 3},{2,5},{3,7}};

        System.out.println(maxNonOverlappingCourses(courses));
        System.out.printf("len arr courses: %s\n", courses.length);

        for (var x : courses) {
            System.out.print(Arrays.toString(x));
            System.out.println("");
        }
    }
}
