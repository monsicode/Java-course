package bg.sofia.uni.fmi.mjt.glovo.delivery;

import java.util.Comparator;

public class DeliveryInfoPriceComparator implements Comparator<DeliveryInfo> {

    @Override
    public int compare(DeliveryInfo o1, DeliveryInfo o2) {
        return Double.compare(o1.price(), o2.price());
    }
}
