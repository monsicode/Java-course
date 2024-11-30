package bg.sofia.uni.fmi.mjt.glovo;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.Delivery;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidMapLayoutException;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidMapSymbolException;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidOrderException;
import bg.sofia.uni.fmi.mjt.glovo.exception.NoAvailableDeliveryGuyException;

public class MainTest {

    private static final int X = 3;
    private static final int MAX_TIME = 10;

    public static void main(String[] args) {

        char[][] layout = {
            // x: 0    1    2    3    4   /y
            {'.', 'A', 'B', 'A', '#'}, // 0
            {'R', '.', '#', 'B', '.'}, // 1
            {'.', '.', '#', '.', '#'}, // 2
            {'#', 'C', '#', 'A', '.'}, // 3
            {'#', '.', '#', '#', '#'}  // 4
        };

        MapEntity client = new MapEntity(new Location(X, 1), MapEntityType.CLIENT);
        MapEntity restaurant = new MapEntity(new Location(1, 0), MapEntityType.RESTAURANT);

//        ControlCenter controlCenter = new ControlCenter(layout);
//        System.out.println(Arrays.deepToString(controlCenter.getLayout()));

        try {
            Glovo app = new Glovo(layout);
            //Delivery delivery = app.getCheapestDelivery(client, restaurant, "pizza");
            Delivery delivery = app.getCheapestDeliveryWithinTimeLimit(client, restaurant, "pizza", MAX_TIME);
            System.out.println("\n" + delivery);
        } catch (InvalidMapLayoutException | InvalidMapSymbolException | NoAvailableDeliveryGuyException |
                 InvalidOrderException | IllegalArgumentException err) {
            System.out.println(err.getMessage());
        }
    }

}
