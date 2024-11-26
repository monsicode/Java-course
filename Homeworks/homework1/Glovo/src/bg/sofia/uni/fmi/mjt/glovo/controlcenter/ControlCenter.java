package bg.sofia.uni.fmi.mjt.glovo.controlcenter;

import bg.sofia.uni.fmi.mjt.glovo.pathAlgorithm.AStarAlgorithm;
import bg.sofia.uni.fmi.mjt.glovo.Utils;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;
import bg.sofia.uni.fmi.mjt.glovo.pathAlgorithm.Cell;

import java.util.List;

import static bg.sofia.uni.fmi.mjt.glovo.Utils.nullCheck;


public class ControlCenter implements ControlCenterApi {

    private final char[][] mapLayout;

    public ControlCenter(char[][] mapLayout) {
        this.mapLayout = mapLayout;
    }

    @Override
    public DeliveryInfo findOptimalDeliveryGuy(Location restaurantLocation, Location clientLocation, double maxPrice,
                                               int maxTime, ShippingMethod shippingMethod) {

        nullCheck(restaurantLocation, "Restaurant locations cannot be null");
        nullCheck(clientLocation, "Client locations cannot be null");
        nullCheck(maxPrice, "Max price cannot be null");
        nullCheck(maxTime, "Max time cannot be null");
        nullCheck(shippingMethod, "Shipping method cannot be null");


        //I use list of cells bc I need a cell to do the A* algorithm
        List<Cell> allDeliveryGuysLocation = Utils.getAllStartingPoints(mapLayout);

        AStarAlgorithm startLocation;
        Cell resturantCell = new Cell(restaurantLocation.x(), restaurantLocation.y());

        for (var cell : allDeliveryGuysLocation) {
            startLocation = new AStarAlgorithm(mapLayout, cell, resturantCell);
            int pathDistance = startLocation.findPath();
            System.out.println(
                "\nThe distance for " + "(" + cell.getRow() + ", " + cell.getCol() + ")" + "is: " + pathDistance +
                    " km. \n");
        }

        return null;
    }

    @Override
    public MapEntity[][] getLayout() {
        return new MapEntity[0][];
    }
}
