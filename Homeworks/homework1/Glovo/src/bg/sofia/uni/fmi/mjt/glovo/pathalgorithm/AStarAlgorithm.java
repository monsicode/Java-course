package bg.sofia.uni.fmi.mjt.glovo.pathalgorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class AStarAlgorithm {
    private static final int[] UP = {-1, 0};
    private static final int[] DOWN = {1, 0};
    private static final int[] LEFT = {0, -1};
    private static final int[] RIGHT = {0, 1};

    private final char[][] mapLayout;
    private final Cell start;
    private final Cell end;
    private final Queue<Cell> open;
    private final Set<Cell> visited;

    public AStarAlgorithm(char[][] grid, Cell start, Cell end) {
        this.mapLayout = grid;
        this.start = start;
        this.end = end;
        this.open = new PriorityQueue<>(new CellComparator());
        this.visited = new HashSet<>();
    }

    private int heuristic(Cell current) {
        return Math.abs(current.getRow() - end.getRow()) + Math.abs(current.getCol() - end.getCol());
    }

    private List<Cell> getNeighbours(Cell node) {
        List<Cell> neighbours = new ArrayList<>();
        int[][] directions = {UP, DOWN, LEFT, RIGHT};

        for (int[] dir : directions) {
            int row = node.getRow() + dir[0];
            int col = node.getCol() + dir[1];

            if (row >= 0 && row < mapLayout.length && col >= 0 && col < mapLayout[0].length &&
                mapLayout[row][col] != '#') {
                neighbours.add(new Cell(row, col));
            }
        }

        return neighbours;
    }

    public int findPath() {
        start.setGCost(0);
        start.setFCost(heuristic(start));
        open.add(start);

        while (!open.isEmpty()) {
            Cell current = open.poll();

            if (current.equals(end)) {
                return current.getGCost();
            }

            visited.add(current);
            for (Cell neighbour : getNeighbours(current)) {
                if (visited.contains(neighbour)) continue;

                int tentativeG = current.getGCost() + 1;

                if (!open.contains(neighbour) || tentativeG < neighbour.getGCost()) {
                    neighbour.setGCost(tentativeG);
                    neighbour.setFCost(tentativeG + heuristic(neighbour));

                    if (!open.contains(neighbour)) {
                        open.add(neighbour);
                    }
                }
            }
        }
        return 0;
    }

}