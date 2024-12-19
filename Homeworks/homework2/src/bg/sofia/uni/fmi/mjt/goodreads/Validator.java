package bg.sofia.uni.fmi.mjt.goodreads;

public class Validator {

    public static <T> void nullCheck(T obj, String errMessage) {
        if (obj == null) {
            throw new IllegalArgumentException(errMessage);
        }
    }

    public static void emptyStringCheck(String obj, String errMessage) {
        if (obj.isEmpty()) {
            throw new IllegalArgumentException(errMessage);
        }
    }

}
