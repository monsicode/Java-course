package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

public enum FuelType {
    DIESEL(3),
    PETROL(3),
    HYBRID(1),
    ELECTRICITY(0),
    HYDROGEN(0);

    private final int priceFuel;

    FuelType(int priceFuel){
        this.priceFuel = priceFuel;
    }

   public int getPriceFuel(){
        return priceFuel;
   }
}
