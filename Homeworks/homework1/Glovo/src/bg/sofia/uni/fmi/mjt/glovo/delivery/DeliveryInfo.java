package bg.sofia.uni.fmi.mjt.glovo.delivery;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;

import static bg.sofia.uni.fmi.mjt.glovo.Utils.nullCheck;

public record DeliveryInfo(Location deliveryGuyLocation, double price, int estimatedTime, DeliveryType deliveryType) {
    public DeliveryInfo {
        nullCheck(deliveryGuyLocation, "Delivery guy location cannot be null");
        nullCheck(price, "Price cannot be null");
        nullCheck(estimatedTime, "Time cannot be null");
        nullCheck(deliveryType, "Driver type cannot be null");
    }
}
