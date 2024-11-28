package bg.sofia.uni.fmi.mjt.glovo.controlcenter.deliverymethod;

import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryType;
import bg.sofia.uni.fmi.mjt.glovo.pathAlgorithm.Cell;

import java.util.Map;

public class CheapestDelivery implements DeliveryMethod {

    @Override
    public double calculate(Map<Cell, DeliveryType> deliveryGuy, Cell curCell, int pathDistance) {
        return deliveryGuy.get(curCell).getPricePerKm() * pathDistance;
    }
}
