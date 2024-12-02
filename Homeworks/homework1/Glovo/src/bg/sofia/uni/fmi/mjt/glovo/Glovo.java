package bg.sofia.uni.fmi.mjt.glovo;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.ControlCenter;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.delivery.Delivery;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidOrderException;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidPriceException;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidTimeException;
import bg.sofia.uni.fmi.mjt.glovo.exception.NoAvailableDeliveryGuyException;

import java.util.Arrays;

import static bg.sofia.uni.fmi.mjt.glovo.Utils.nullCheck;

public class Glovo implements GlovoApi {

    private static final double MIN_PRICE = 1.0;

    private char[][] mapLayout;
    private final ControlCenter controlCenter;

    public Glovo(char[][] mapLayout) {

        controlCenter = new ControlCenter(mapLayout);
        setMapLayout(mapLayout);

    }

    private void setMapLayout(char[][] layout) {
        mapLayout = new char[layout.length][];

        for (int i = 0; i < layout.length; i++) {
            mapLayout[i] = Arrays.copyOf(layout[i], layout[i].length);
        }

    }

    @Override
    public Delivery getCheapestDelivery(MapEntity client, MapEntity restaurant, String foodItem)
        throws NoAvailableDeliveryGuyException {

        return calculateOptimalDeliveryByMethod(client, restaurant, foodItem, ShippingMethod.CHEAPEST, -1, -1);

    }

    @Override
    public Delivery getFastestDelivery(MapEntity client, MapEntity restaurant, String foodItem)
        throws NoAvailableDeliveryGuyException {

        return calculateOptimalDeliveryByMethod(client, restaurant, foodItem, ShippingMethod.FASTEST, -1, -1);

    }

    @Override
    public Delivery getFastestDeliveryUnderPrice(MapEntity client, MapEntity restaurant, String foodItem,
                                                 double maxPrice) throws NoAvailableDeliveryGuyException {

        validatePrice(maxPrice);
        return calculateOptimalDeliveryByMethod(client, restaurant, foodItem, ShippingMethod.FASTEST, maxPrice, -1);
    }

    @Override
    public Delivery getCheapestDeliveryWithinTimeLimit(MapEntity client, MapEntity restaurant, String foodItem,
                                                       int maxTime) throws NoAvailableDeliveryGuyException {

        validateTime(maxTime);
        return calculateOptimalDeliveryByMethod(client, restaurant, foodItem, ShippingMethod.CHEAPEST, -1, maxTime);
    }

    private Delivery calculateOptimalDeliveryByMethod(MapEntity client, MapEntity restaurant, String foodItem,
                                                      ShippingMethod shippingMethod, double maxPrice, int maxTime)
        throws NoAvailableDeliveryGuyException {

        validateInput(client, restaurant, foodItem);
        validateLocations(client, restaurant);

        Delivery delivery = null;

        DeliveryInfo deliveryInfo =
            controlCenter.findOptimalDeliveryGuy(restaurant.location(), client.location(), maxPrice, maxTime,
                shippingMethod);

        if (deliveryInfo == null) {
            throw new NoAvailableDeliveryGuyException("No available guy found for the requested order");
        }

        delivery = new Delivery(restaurant.location(), client.location(), deliveryInfo.deliveryGuyLocation(), foodItem,
            deliveryInfo.price(), deliveryInfo.estimatedTime());

        return delivery;
    }

    private boolean isLocationInBoundary(MapEntity endPoint) {
        int row = endPoint.location().x();
        int col = endPoint.location().y();

        return row >= 0 && row < mapLayout.length && col >= 0 && col < mapLayout[0].length;
    }

    private boolean isValidLocation(MapEntity endPoint) {

        if (!isLocationInBoundary(endPoint)) {
            return false;
        }

        int row = endPoint.location().x();
        int col = endPoint.location().y();

        return mapLayout[row][col] == endPoint.type().getCharacter();

    }

    private void validateLocation(MapEntity endPoint) {
        if (!isLocationInBoundary(endPoint)) {
            throw new InvalidOrderException("Invalid location for " + endPoint.type());
        }

        if (!isValidLocation(endPoint)) {
            throw new InvalidOrderException("No " + endPoint.type() + " found at location: " + endPoint.location());
        }
    }

    private void validateLocations(MapEntity client, MapEntity restaurant) {
        validateLocation(client);
        validateLocation(restaurant);
    }

    private void validateInput(MapEntity client, MapEntity restaurant, String foodItem) {
        nullCheck(client, "Client cannot be null");
        nullCheck(restaurant, "Restaurant cannot be null");
        nullCheck(foodItem, "FoodItem cannot be null");
    }

    private void validatePrice(double price) {
        if (Double.compare(price, MIN_PRICE) < 0) {
            throw new InvalidPriceException("Price for delivery cannot be under 1lv.");
        }
    }

    private void validateTime(int maxTime) {
        if (maxTime < 0) {
            throw new InvalidTimeException("Time for delivery cannot be under 0 min.");
        }
    }

}
