package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class MotorVehicle extends Vehicle {

    protected static final int PRICE_PER_SEAT = 5;

    // we are not going to change the data --> final
    protected final FuelType fuelType;
    protected final int numberOfSeats;
    protected final double pricePerWeek;


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
        long totalDays = rentalDuration.toDays();
        long weeks = totalDays / 7;
        long days = totalDays % 7;
        long hours = rentalDuration.toHours() % 24;

        double priceSeats = numberOfSeats * PRICE_PER_SEAT;

        double price = (weeks * pricePerWeek) + (days * pricePerDay) + (hours * pricePerHour) + priceSeats + driver.ageGroup().getPriceRent() + fuelType.getPriceFuel()*days;

        return price;
    }

}
