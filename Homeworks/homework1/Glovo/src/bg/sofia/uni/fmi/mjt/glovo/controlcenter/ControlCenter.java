package bg.sofia.uni.fmi.mjt.glovo.controlcenter;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryType;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidMapLayoutException;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidMapSymbolException;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidPriceException;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidTimeException;
import bg.sofia.uni.fmi.mjt.glovo.pathalgorithm.AStarAlgorithm;
import bg.sofia.uni.fmi.mjt.glovo.Utils;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;
import bg.sofia.uni.fmi.mjt.glovo.pathalgorithm.Cell;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static bg.sofia.uni.fmi.mjt.glovo.Utils.nullCheck;

public class ControlCenter implements ControlCenterApi {

    private static final double NO_COST_CONSTRAIN = -1;
    private static final int NO_TIME_CONSTRAIN = -1;

    private char[][] mapLayout;

    public ControlCenter(char[][] mapLayout) {
        setMapLayout(mapLayout);
    }

    private void setMapLayout(char[][] layout) {

        nullCheck(layout, "Map cannot be null!");
        areRowsEqualLength(layout);
        areMapSymbolsValid(layout);

        mapLayout = new char[layout.length][];

        for (int i = 0; i < layout.length; i++) {
            mapLayout[i] = Arrays.copyOf(layout[i], layout[i].length);
        }

    }

    private void areRowsEqualLength(char[][] layout) {
        int rowLength = layout[0].length;
        for (int i = 1; i < layout.length; i++) {
            if (layout[i].length != rowLength) {
                throw new InvalidMapLayoutException("All rows in the map layout must have the same length.");
            }
        }
    }

    private boolean isValidMapSymbol(char ch) {
        return Utils.isMapEntity(ch);
    }

    private void areMapSymbolsValid(char[][] layout) {
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                char c = layout[i][j];
                if (!isValidMapSymbol(c)) {
                    throw new InvalidMapSymbolException("Invalid character in map layout: " + c);
                }
            }
        }
    }

    @Override
    public DeliveryInfo findOptimalDeliveryGuy(Location restaurantLocation, Location clientLocation, double maxPrice,
                                               int maxTime, ShippingMethod shippingMethod) {
        validateInput(restaurantLocation, clientLocation, maxPrice, maxTime, shippingMethod);
        validatePrice(maxPrice);
        validateTime(maxTime);

        DeliveryInfo bestDeliveryInfo = null;

        List<Cell> allDeliveryGuysLocation = Utils.getAllStartingPoints(mapLayout);
        Map<Cell, DeliveryType> deliveryTypes = Utils.getDeliveryGuys();

        int distanceFromRestaurantToClient = calculateDeliveryDistanceToClient(restaurantLocation, clientLocation);

        if (distanceFromRestaurantToClient == 0) {
            return bestDeliveryInfo;
        }

        for (Cell curDeliveryGuy : allDeliveryGuysLocation) {

            int distanceToRestaurant = calculateDistanceToRestaurant(curDeliveryGuy, restaurantLocation);

            DeliveryInfo curDeliveryInfo =
                getCurDeliveryInfo(curDeliveryGuy, distanceToRestaurant, distanceFromRestaurantToClient,
                    deliveryTypes.get(curDeliveryGuy));

            if (isDeliveryInfoOptimal(curDeliveryInfo, maxPrice, maxTime)) {
                bestDeliveryInfo = chooseBestDeliveryByMethod(bestDeliveryInfo, curDeliveryInfo, shippingMethod);
            }
        }
        return bestDeliveryInfo;
    }

    private int calculateDistanceToRestaurant(Cell deliveryGuy, Location restaurantLocation) {
        Cell restaurant = new Cell(restaurantLocation.x(), restaurantLocation.y());

        AStarAlgorithm algorithmShortestPath = new AStarAlgorithm(mapLayout, deliveryGuy, restaurant);

        return algorithmShortestPath.findPath();
    }

    private DeliveryInfo getCurDeliveryInfo(Cell deliveryGuy, int distanceToRestaurant, int distanceToClient,
                                            DeliveryType deliveryType) {
        Location deliveryGuyLocation = new Location(deliveryGuy.getRow(), deliveryGuy.getCol());
        double priceDelivery = (distanceToRestaurant + distanceToClient) * deliveryType.getPricePerKm();
        int estimateTime = (distanceToRestaurant + distanceToClient) * deliveryType.getTimePerKm();

        return new DeliveryInfo(deliveryGuyLocation, priceDelivery, estimateTime, deliveryType);
    }

    private boolean isDeliveryInfoOptimal(DeliveryInfo deliveryInfo, double maxPrice, int maxTime) {
        return (Double.compare(maxPrice, NO_COST_CONSTRAIN) == 0 || deliveryInfo.price() <= maxPrice) &&
            (maxTime == NO_TIME_CONSTRAIN || deliveryInfo.estimatedTime() <= maxTime);
    }

    private DeliveryInfo chooseBestDeliveryByMethod(DeliveryInfo bestOption, DeliveryInfo curOption,
                                                    ShippingMethod shippingMethod) {
        if (bestOption == null) {
            return curOption;
        }

        return switch (shippingMethod) {
            case FASTEST -> curOption.estimatedTime() < bestOption.estimatedTime() ? curOption : bestOption;
            case CHEAPEST -> curOption.price() < bestOption.price() ? curOption : bestOption;
        };
    }

    private void validateInput(Location restaurantLocation, Location clientLocation, double maxPrice,
                               int maxTime, ShippingMethod shippingMethod) {
        nullCheck(restaurantLocation, "Restaurant locations cannot be null");
        nullCheck(clientLocation, "Client locations cannot be null");
        nullCheck(maxPrice, "Max price cannot be null");
        nullCheck(maxTime, "Max time cannot be null");
        nullCheck(shippingMethod, "Shipping method cannot be null");
    }

    private int calculateDeliveryDistanceToClient(Location restaurantLocation, Location clientLocation) {

        Cell cellStart = new Cell(restaurantLocation.x(), restaurantLocation.y());
        Cell cellEnd = new Cell(clientLocation.x(), clientLocation.y());

        AStarAlgorithm algorithmShortestPath = new AStarAlgorithm(mapLayout, cellStart, cellEnd);

        return algorithmShortestPath.findPath();
    }

    @Override
    public MapEntity[][] getLayout() {
        MapEntity[][] layout = new MapEntity[mapLayout.length][];

        for (int i = 0; i < mapLayout.length; i++) {
            layout[i] = new MapEntity[mapLayout[i].length];

            for (int j = 0; j < mapLayout[i].length; j++) {
                char symbol = mapLayout[i][j];

                MapEntityType type = Utils.fromChar(symbol);

                layout[i][j] = new MapEntity(new Location(i, j), type);
            }
        }

        return layout;
    }

    private void validatePrice(double price) {
        if (price < 0 && Double.compare(price, NO_COST_CONSTRAIN) != 0) {
            throw new InvalidPriceException("Price for delivery cannot be under -1.");
        }
    }

    private void validateTime(int maxTime) {
        if (maxTime != NO_TIME_CONSTRAIN && maxTime < 0) {
            throw new InvalidTimeException("Time for delivery cannot be under -1");
        }
    }

}
