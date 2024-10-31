package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;

public class MotorVehicle extends Vehicle {

    protected static final int PRICE_PER_SEAT = 5;

    // we are not going to change the data --> final
    protected final FuelType fuelType;
    protected final int numberOfSeats;
    protected final double pricePerWeek;
    protected final double pricePerDay;
    protected final double pricePerHour;


    public MotorVehicle(String id, String model, FuelType fuelType, int numberOfSeats, double pricePerWeek, double pricePerDay, double pricePerHour) {
        super(id, model);
        this.fuelType = fuelType;
        this.numberOfSeats = numberOfSeats;
        this.pricePerWeek = pricePerWeek;
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        if (startOfRent.isAfter(endOfRent)) {
            throw new InvalidRentingPeriodException("Invalid renting period ! ");
        }

        Duration rentalDuration = Duration.between(startOfRent, endOfRent);
        long hours = rentalDuration.toHoursPart();
        long days = rentalDuration.toDays() % 7;
        long weeks =  rentalDuration.toDays() / 7;

        double priceSeats = numberOfSeats * PRICE_PER_SEAT;

        double price = (weeks * pricePerWeek) + (days * pricePerDay) + (hours * pricePerHour) + priceSeats + driver.ageGroup().getPriceRent() + fuelType.getPriceFuel();

        return price;
    }

}
