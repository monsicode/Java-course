package bg.sofia.uni.fmi.mjt.glovo.controlcenter.map;

import static bg.sofia.uni.fmi.mjt.glovo.Utils.nullCheck;

public record Location(int x, int y) {
    public Location {
        nullCheck(x, "X cannot be null");
        nullCheck(y, "Y cannot be null");
    }
}
