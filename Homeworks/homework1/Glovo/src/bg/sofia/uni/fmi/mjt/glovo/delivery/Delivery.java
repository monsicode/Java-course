package bg.sofia.uni.fmi.mjt.glovo.delivery;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;

import static bg.sofia.uni.fmi.mjt.glovo.Utils.nullCheck;

public record Delivery(Location client, Location restaurant, Location deliveryGuy, String foodItem, double price,
                       int estimatedTime) {
    public Delivery {
        nullCheck(client, "Client location cannot be null");
        nullCheck(restaurant, "Restaurant location cannot be null");
        nullCheck(deliveryGuy, "Delivery guy location cannot be null");
        nullCheck(foodItem, "FoodItem cannot be null");
        nullCheck(price, "Price cannot be null");
        nullCheck(estimatedTime, "Time cannot be null");
    }
}


