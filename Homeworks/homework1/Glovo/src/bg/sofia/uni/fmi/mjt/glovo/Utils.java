package bg.sofia.uni.fmi.mjt.glovo;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryType;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidMapSymbolException;
import bg.sofia.uni.fmi.mjt.glovo.pathalgorithm.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {


    private static List<Cell> allStartingPoints;
    private static final Map<Cell, DeliveryType> DELIVERY_GUYS_BY_LOCATION = new HashMap<>();

    public static Map<Cell, DeliveryType> getDeliveryGuys() {
        return Collections.unmodifiableMap(DELIVERY_GUYS_BY_LOCATION);
    }

    public static List<Cell> getAllStartingPoints(char[][] grid) {
        if (allStartingPoints == null) {
            allStartingPoints = findAllStartingPoints(grid);
        }
        return allStartingPoints;
    }

    private static List<Cell> findAllStartingPoints(char[][] grid) {
        List<Cell> startingPoints = new ArrayList<>();

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {

                if (grid[x][y] == MapEntityType.DELIVERY_GUY_CAR.getCharacter()) {

                    startingPoints.add(new Cell(x, y));
                    DELIVERY_GUYS_BY_LOCATION.putIfAbsent(new Cell(x, y), DeliveryType.CAR);

                } else if (grid[x][y] == MapEntityType.DELIVERY_GUY_BIKE.getCharacter()) {

                    startingPoints.add(new Cell(x, y));
                    DELIVERY_GUYS_BY_LOCATION.putIfAbsent(new Cell(x, y), DeliveryType.BIKE);

                }
            }
        }

        return startingPoints;
    }

    public static <T> void nullCheck(T obj, String errMessage) {
        if (obj == null) {
            throw new IllegalArgumentException(errMessage);
        }
    }

    public static boolean isMapEntity(char ch) {
        for (MapEntityType type : MapEntityType.values()) {
            if (type.getCharacter() == ch) {
                return true;
            }
        }
        return false;
    }

    public static MapEntityType fromChar(char ch) {
        for (MapEntityType type : MapEntityType.values()) {
            if (type.getCharacter() == ch) {
                return type;
            }
        }
        throw new InvalidMapSymbolException("Invalid character: " + ch);
    }

}
