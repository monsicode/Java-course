package bg.sofia.uni.fmi.mjt.vehiclerent.driver;

public enum AgeGroup{
    JUNIOR(10),
    EXPERIENCED(0),
    SENIOR(15);

    private final int priceRent;

    AgeGroup(int priceRent){
        this.priceRent = priceRent;
    }

    public int getPriceRent(){
        return priceRent;
    }
}
