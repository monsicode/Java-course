package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;

public class Caravan extends MotorVehicle {

    private static final int PRICE_PER_BED = 10;

    private final int numberOfBeds;

    public Caravan(String id, String model, FuelType fuelType, int numberOfSeats, int numberOfBeds, double pricePerWeek, double pricePerDay, double pricePerHour) {
        super(id, model, fuelType, numberOfSeats, pricePerWeek, pricePerDay, pricePerHour);
        this.numberOfBeds = numberOfBeds;
    }


    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        if (startOfRent.isAfter(endOfRent)) {
            throw new InvalidRentingPeriodException("Invalid renting period ! ");
        }



        Duration rentalDuration = Duration.between(startOfRent, endOfRent);
        if (rentalDuration.compareTo(Duration.ofDays(1)) < 0) {
            throw new InvalidRentingPeriodException("Renting period is under one day!");
        }

        double basePrice = super.calculateRentalPrice(startOfRent, endOfRent);
        double priceBeds = numberOfBeds * PRICE_PER_BED;

        return basePrice + priceBeds;
    }
}
