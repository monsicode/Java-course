package bg.sofia.uni.fmi.mjt.glovo;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<AStarAlgorithm.Cell> findAllStartingPoints(char[][] grid) {
        List<AStarAlgorithm.Cell> startingPoints = new ArrayList<>();
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                if (grid[x][y] == 'A' || grid[x][y] == 'B') {
                    startingPoints.add(new AStarAlgorithm.Cell(x, y));

                }
            }
        }
        return startingPoints;
    }

}
