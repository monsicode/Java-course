package bg.sofia.uni.fmi.mjt.imagekit;

public class NullCheck {
    public static <T> void validateObj(T obj, String errMessage) {
        if (obj == null) {
            throw new IllegalArgumentException(errMessage);
        }
    }
}
