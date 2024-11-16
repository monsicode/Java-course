package bg.sofia.uni.fmi.mjt.math;

public class NumberUtils {

    boolean isPrime(int number) {
        if (number < 2){
            throw new IllegalArgumentException("sffa");
        }

            for (int i = 2; i <= Math.sqrt(number); i++) {
                if (number % i == 0) {
                    return false;
                }
            }
        return true;
    }
}
