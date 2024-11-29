package bg.sofia.uni.fmi.mjt.glovo.controlcenter;

import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryType;
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

    private char[][] mapLayout;

    public ControlCenter(char[][] mapLayout) {
        setMapLayout(mapLayout);
    }

    private void setMapLayout(char[][] layout) {

        mapLayout = new char[layout.length][];

        for (int i = 0; i < layout.length; i++) {
            mapLayout[i] = Arrays.copyOf(layout[i], layout[i].length);
        }

    }

    @Override
    public DeliveryInfo findOptimalDeliveryGuy(Location restaurantLocation, Location clientLocation, double maxPrice,
                                               int maxTime, ShippingMethod shippingMethod) {

        validateInput(restaurantLocation, clientLocation, maxPrice, maxTime, shippingMethod);

        DeliveryInfo bestDeliveryInfo = null;

        List<Cell> allDeliveryGuysLocation = Utils.getAllStartingPoints(mapLayout);
        Map<Cell, DeliveryType> deliveryTypes = Utils.getDeliveryGuys();

        int distanceFromRestaurantToClient = calculateDeliveryDistanceToClient(restaurantLocation, clientLocation);

        for (Cell curDeliveryGuy : allDeliveryGuysLocation) {

            int distanceToRestaurant = calculateDistanceToRestaurant(curDeliveryGuy, restaurantLocation);

            DeliveryInfo curDeliveryInfo =
                getCurDeliveryInfo(curDeliveryGuy, distanceToRestaurant, distanceFromRestaurantToClient,
                    deliveryTypes.get(curDeliveryGuy));

            if (!isDeliveryInfoOptimal(curDeliveryInfo, maxPrice, maxTime)) {
                continue;
            }

            bestDeliveryInfo = chooseBestDeliveryByMethod(bestDeliveryInfo, curDeliveryInfo, shippingMethod);
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
        return (maxPrice == -1 || deliveryInfo.price() <= maxPrice) &&
            (maxTime == -1 || deliveryInfo.estimatedTime() <= maxTime);
    }

    private DeliveryInfo chooseBestDeliveryByMethod(DeliveryInfo bestOption, DeliveryInfo curOption,
                                                    ShippingMethod shippingMethod) {
        if (bestOption == null) {
            return curOption;
        }

        return switch (shippingMethod) {
            case FASTEST -> curOption.estimatedTime() < bestOption.estimatedTime() ? curOption : bestOption;
            case CHEAPEST -> curOption.price() < bestOption.price() ? curOption : bestOption;
            default -> throw new IllegalArgumentException("Unknown shipping method");
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
//        MapEntity layout = new MapEntity[mapLayout.length][];
//
//        for (int i = 0; i < mapLayout.length; i++) {
//            mapLayout[i] = Arrays.copyOf(mapLayout[i], mapLayout[i].length);
//        }
//        return layout;
        return null;
    }
}
