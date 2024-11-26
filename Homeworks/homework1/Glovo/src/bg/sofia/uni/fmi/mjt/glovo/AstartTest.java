package bg.sofia.uni.fmi.mjt.glovo;

import java.util.*;

class AStar {
    private static final int[] UP = {-1, 0};
    private static final int[] DOWN = {1, 0};
    private static final int[] LEFT = {0, -1};
    private static final int[] RIGHT = {0, 1};

    private final char[][] mapLayout;
    private final Cell start;
    private final Cell end;
    private final Queue<Cell> open;
    private final Set<Cell> visited;
    private final Map<Cell, Cell> cameFrom;

    public AStar(char[][] grid, Cell start, Cell end) {
        this.mapLayout = grid;
        this.start = start;
        this.end = end;
        this.open = new PriorityQueue<>(Comparator.comparingInt(n -> n.fCost));
        this.visited = new HashSet<>();
        this.cameFrom = new HashMap<>();
    }

    private int heuristic(Cell current) {
        return Math.abs(current.row - end.row) + Math.abs(current.col - end.col);
    }

    private List<Cell> getNeighbours(Cell node) {
        List<Cell> neighbours = new ArrayList<>();
        int[][] directions = {UP, DOWN, LEFT, RIGHT};

        for (int[] dir : directions) {
            int row = node.row + dir[0];
            int col = node.col + dir[1];

            if (row >= 0 && row < mapLayout.length && col >= 0 && col < mapLayout[0].length &&
                mapLayout[row][col] != '#') {
                neighbours.add(new Cell(row, col));
            }
        }

        return neighbours;
    }

    public List<Cell> findPath() {
        start.gCost = 0;
        start.fCost = heuristic(start);
        open.add(start);

        while (!open.isEmpty()) {
            Cell current = open.poll();

            if (current.equals(end)) {
                return reconstructPath(current);
            }

            visited.add(current);

            for (Cell neighbour : getNeighbours(current)) {
                if (visited.contains(neighbour)) continue;

                int tentativeG = current.gCost + 1;

                if (!open.contains(neighbour) || tentativeG < neighbour.gCost) {
                    cameFrom.put(neighbour, current);
                    neighbour.gCost = tentativeG;
                    neighbour.fCost = tentativeG + heuristic(neighbour);

                    if (!open.contains(neighbour)) {
                        open.add(neighbour);
                    }
                }
            }
        }

        return Collections.emptyList();
    }

    private List<Cell> reconstructPath(Cell current) {
        List<Cell> path = new ArrayList<>();
        while (cameFrom.containsKey(current)) {
            path.add(current);
            current = cameFrom.get(current);
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }

    static class Cell {
        int row, col;
        int gCost;
        int fCost; // (g + h)


        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
            this.gCost = Integer.MAX_VALUE;
            this.fCost = Integer.MAX_VALUE;
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

    public static void main(String[] args) {
        char[][] layout = {
            // x: 0    1    2    3    4   /y
            {'#', '.', 'R', '#', '#'},// 0
            {'#', '.', '#', '.', '.'},// 1
            {'.', '.', '#', '.', '#'},// 2
            {'#', 'C', '.', 'A', '.'},// 3
            {'#', '.', '#', '#', '#'} // 4
        };

        // Начална и крайна точка
        Cell start = new Cell(3, 3); // 'A' (начало)
        Cell end = new Cell(0, 2);   // 'R' (край)

        AStar aStar = new AStar(layout, start, end);
        List<Cell> path = aStar.findPath();

        if (path.isEmpty()) {
            System.out.println("No path found");
        } else {
            for (Cell node : path) {
                System.out.println("(" + node.row + ", " + node.col + ")");
            }
        }
    }
}
