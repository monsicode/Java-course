package bg.sofia.uni.fmi.mjt.glovo.pathAlgorithm;

import bg.sofia.uni.fmi.mjt.glovo.Utils;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.deliverymethod.CheapestDelivery;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.deliverymethod.DeliveryMethod;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.deliverymethod.FastestDelivery;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfoPriceComparator;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfoTimeComparator;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;

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

    // to be static
    public int findPath() {
        start.setgCost(0);
        start.setfCost(heuristic(start));
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
                    neighbour.setgCost(tentativeG);
                    neighbour.setfCost(tentativeG + heuristic(neighbour));

                    if (!open.contains(neighbour)) {
                        open.add(neighbour);
                    }
                }
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        char[][] layout = {
            // x: 0    1    2    3    4   /y
            {'.', 'A', 'B', '.', '#'},// 0
            {'R', '.', '#', 'B', '.'},// 1
            {'.', '.', '#', '.', '#'},// 2
            {'#', 'C', '#', 'A', '.'},// 3
            {'#', '.', '#', '#', '#'} // 4
        };

        Cell end = new Cell(1, 0);   // 'R' (край)

        DeliveryMethod cheapestDelivery = new CheapestDelivery();
        DeliveryMethod fastestDelivery = new FastestDelivery();

        int result = findByCriteria(layout, end, cheapestDelivery);
        int fastest = findByCriteria(layout, end, fastestDelivery);

        System.out.println("\nCheapest path: " + result);
        System.out.println("\nFastest path: " + fastest);

    }

    private static <T extends DeliveryMethod> int findByCriteria(char[][] layout, Cell end, T function) {
        List<Cell> allDeliveryGuysLocation = Utils.getAllStartingPoints(layout);

        Map<Cell, DeliveryType> deliveryTypes = Utils.getDeliveryGuys();

        int minResult = Integer.MAX_VALUE;

        AStarAlgorithm startLocation;

        for (Cell cell : allDeliveryGuysLocation) {
            startLocation = new AStarAlgorithm(layout, cell, end);
            int pathDistance = startLocation.findPath();

            int currentResult = function.calculate(deliveryTypes, cell, pathDistance);

            if (minResult > currentResult) {
                minResult = currentResult;
            }
        }
        return minResult;
    }


    private class TesterClass {

        //       Location restaurant
        private final Map<Cell, List<DeliveryInfo>> deliveryInfosForRestaurant = new HashMap<>();

        private List<DeliveryInfo> findAllDeliveryInfo(char[][] layout, Cell end) {

            List<Cell> allDeliveryGuysLocation = Utils.getAllStartingPoints(layout);
            Map<Cell, DeliveryType> deliveryTypes = Utils.getDeliveryGuys();

            List<DeliveryInfo> deliveryInfos = new ArrayList<>();
            AStarAlgorithm startLocation;

            for (Cell cell : allDeliveryGuysLocation) {
                startLocation = new AStarAlgorithm(layout, cell, end);
                int pathDistance = startLocation.findPath();

                Location deliveryGuyLocation = new Location(cell.getRow(), cell.getCol());
                DeliveryType deliveryType = deliveryTypes.get(cell);
                double priceDelivery = pathDistance * deliveryType.getPricePerKm();
                int estimateTime = pathDistance * deliveryType.getTimePerKm();

                //if....

                DeliveryInfo deliveryInfo =
                    new DeliveryInfo(deliveryGuyLocation, priceDelivery, estimateTime, deliveryType);

                deliveryInfos.add(deliveryInfo);

            }

//            switch (shippingMethod) {
//                case FASTEST -> deliveryInfos.sort(new DeliveryInfoTimeComparator());
//                case CHEAPEST -> deliveryInfos.sort(new DeliveryInfoPriceComparator());
//                default -> throw new IllegalArgumentException("Unknown shipping method");
//            }

            return deliveryInfos;
        }

        private void storeDeliveryInfoForRestaurant(char[][] layout, Cell restaurantLocation) {

            if (deliveryInfosForRestaurant.containsKey(restaurantLocation)) {
                return; //Data is saved
            }

//            Map<ShippingMethod, List<DeliveryInfo>> deliveryInfoMap = new HashMap<>();
//            deliveryInfoMap.put(ShippingMethod.FASTEST, findAllDeliveryInfo(layout, restaurantLocation, ShippingMethod.FASTEST));
//            deliveryInfoMap.put(ShippingMethod.CHEAPEST, findAllDeliveryInfo(layout, restaurantLocation, ShippingMethod.CHEAPEST));
//
//
//            deliveryInfosForRestaurant.put(restaurantLocation, deliveryInfoMap);

            deliveryInfosForRestaurant.putIfAbsent(restaurantLocation, findAllDeliveryInfo(layout, restaurantLocation));
        }

        public List<DeliveryInfo> getDeliveryInfoByMethod(ShippingMethod shippingMethod, Cell restaurantLocation) {
            //if == null

            List<DeliveryInfo> deliveryInfos = deliveryInfosForRestaurant.get(restaurantLocation);

            switch (shippingMethod) {
                case FASTEST -> deliveryInfos.sort(new DeliveryInfoTimeComparator());
                case CHEAPEST -> deliveryInfos.sort(new DeliveryInfoPriceComparator());
                default -> throw new IllegalArgumentException("Unknown shipping method");
            }

            return deliveryInfos;
        }


        //private final Map<Cell, Map<ShippingMethod, List<DeliveryInfo>>> deliveryInfosForRestaurant = new HashMap<>();
    }


}


//private static void findFastestDelivery(char[][] layout, Cell end) {
//    List<Cell> allDeliveryGuysLocation = Utils.getAllStartingPoints(layout);
//    Map<Cell, DeliveryType> deliveryGuy = Utils.getDeliveryGuy();
//
//    int minTime = Integer.MAX_VALUE;
//
//    AStarAlgorithm startLocation;
//
//    for (Cell cell : allDeliveryGuysLocation) {
//        startLocation = new AStarAlgorithm(layout, cell, end);
//        int pathDistance = startLocation.findPath();
//
//        int timeForCurDelivery = deliveryGuy.get(cell).getTimePerKm() * pathDistance;
//
//        if (minTime > timeForCurDelivery) {
//            minTime = timeForCurDelivery;
//        }
//    }
//
//    System.out.println("\nFastest delivery: " + minTime);
//}
//
//private static void findCheapestDelivery(char[][] layout, Cell end) {
//    List<Cell> allDeliveryGuysLocation = Utils.getAllStartingPoints(layout);
//    Map<Cell, DeliveryType> deliveryGuy = Utils.getDeliveryGuy();
//
//    int minPrice = Integer.MAX_VALUE;
//
//    AStarAlgorithm startLocation;
//
//    for (Cell cell : allDeliveryGuysLocation) {
//        startLocation = new AStarAlgorithm(layout, cell, end);
//        int pathDistance = startLocation.findPath();
//
//        int timeForCurDelivery = deliveryGuy.get(cell).getPricePerKm() * pathDistance;
//
//        if (minPrice > timeForCurDelivery) {
//            minPrice = timeForCurDelivery;
//        }
//    }
//
//    System.out.println("\nCheapest delivery: " + minPrice);
//}
