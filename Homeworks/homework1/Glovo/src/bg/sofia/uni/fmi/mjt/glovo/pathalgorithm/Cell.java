package bg.sofia.uni.fmi.mjt.glovo.pathalgorithm;

import java.util.Objects;

public class Cell {
    private final int row;
    private final int col;
    private int gCost;
    private int fCost; // (g + h)

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.gCost = Integer.MAX_VALUE;
        this.fCost = Integer.MAX_VALUE;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getGCost() {
        return gCost;
    }

    public int getFCost() {
        return fCost;
    }

    public void setGCost(int gCost) {
        this.gCost = gCost;
    }

    public void setFCost(int fCost) {
        this.fCost = fCost;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Cell cell = (Cell) object;
        return row == cell.row && col == cell.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
