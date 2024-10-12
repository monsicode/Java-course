import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void print(String str)
    {
        System.out.println(str);
    }

    public static void main(String[] args) {

        int[] arr = new int[5];

        //int arr[10];


        int size = arr.length;

        System.out.print(size);

        System.out.printf("Enter somethig: ");
        Scanner enter = new Scanner(System.in);
        String lineRead = enter.nextLine();

        if (lineRead.equals("Hello")) {
            System.out.printf("Congrats!");
        }

    }
}