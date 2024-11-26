package bg.sofia.uni.fmi.mjt.glovo.pathAlgorithm;

import java.util.Objects;

public class Cell {
    private final int row, col;
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

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }

    public void setfCost(int fCost) {
        this.fCost = fCost;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Cell)) return false;
        Cell other = (Cell) obj;
        return this.row == other.row && this.col == other.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

}
