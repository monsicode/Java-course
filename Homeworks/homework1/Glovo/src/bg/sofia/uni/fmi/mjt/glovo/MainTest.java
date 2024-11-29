package bg.sofia.uni.fmi.mjt.glovo;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.ControlCenter;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.Delivery;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidOrderException;
import bg.sofia.uni.fmi.mjt.glovo.exception.NoAvailableDeliveryGuyException;

public class MainTest {

    @SuppressWarnings("checkstyle:MethodLength")
    public static void main(String[] args) {

        char[][] layout = {
            // x: 0    1    2    3    4   /y
            {'.', 'A', 'B', 'A', '#'}, // 0
            {'R', '.', '#', 'B', '.'}, // 1
            {'.', '.', '#', '.', '#'}, // 2
            {'#', 'C', '#', 'A', '.'}, // 3
            {'#', '.', '#', '#', '#'}  // 4
        };

//        ControlCenter controlCenter = new ControlCenter(layout);
//
//        DeliveryInfo deliveryInfo = controlCenter.findOptimalDeliveryGuy(new Location(1, 0),
//            new Location(3, 1), 20, 29,
//            ShippingMethod.CHEAPEST);
//
//       if (deliveryInfo != null) {
//            System.out.println("\n");
//            System.out.println(deliveryInfo);
//        } else {
//            System.out.println("\n");
//            System.out.println("No such a delivery guy exist");
//        }

        Glovo app = new Glovo(layout);

        MapEntity client = new MapEntity(new Location(3, 1), MapEntityType.CLIENT);
        MapEntity restaurant = new MapEntity(new Location(1, 0), MapEntityType.RESTAURANT);

        try {
            //Delivery delivery = app.getCheapestDelivery(client, restaurant, "pizza");
            Delivery delivery = app.getCheapestDeliveryWithinTimeLimit(client, restaurant, "pizza", 10);
            System.out.println("\n" + delivery);
        } catch (NoAvailableDeliveryGuyException | InvalidOrderException e) {
            System.out.println(e.getMessage());
        }
    }

}
