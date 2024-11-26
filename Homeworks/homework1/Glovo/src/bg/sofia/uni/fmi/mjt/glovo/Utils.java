package bg.sofia.uni.fmi.mjt.glovo;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<AStar.Cell> findAllStartingPoints(char[][] grid) {
        List<AStar.Cell> startingPoints = new ArrayList<>();
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                if (grid[x][y] == 'A' || grid[x][y] == 'B') {
                    startingPoints.add(new AStar.Cell(x, y));
                }
            }
        }
        return startingPoints;
    }

}
