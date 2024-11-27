package bg.sofia.uni.fmi.mjt.glovo.pathAlgorithm;

import java.util.Comparator;

public class CellComparator implements Comparator<Cell> {

    @Override
    public int compare(Cell o1, Cell o2) {
        return Integer.compare(o1.getFCost(), o2.getFCost());
    }
}
