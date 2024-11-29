package bg.sofia.uni.fmi.mjt.glovo.delivery;

public enum DeliveryType {
    CAR(5, 3),
    BIKE(3, 5);

    private final double pricePerKm;
    private final int timePerKm;

    DeliveryType(int pricePerKm, int timePerKm) {
        this.pricePerKm = pricePerKm;
        this.timePerKm = timePerKm;
    }

    public double getPricePerKm() {
        return pricePerKm;
    }

    public int getTimePerKm() {
        return timePerKm;
    }
}
