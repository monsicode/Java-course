package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;

public class Car extends Vehicle {

    private FuelType fuelType;
    private int numberOfSeats;
    private double pricePerWeek;
    private double pricePerDay;
    private double pricePerHour;


    public Car(String id, String model, FuelType fuelType, int numberOfSeats, double pricePerWeek, double pricePerDay, double pricePerHour) {
        super(id, model);

        this.fuelType = fuelType;
        this.numberOfSeats = numberOfSeats;
        this.pricePerWeek = pricePerWeek;
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
    }

    @Override
    public void setMinTime() {

    }

    @Override
    public void setMaxTime() {

    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        if(startOfRent.isAfter(endOfRent)){
            throw new InvalidRentingPeriodException("Invalid renting period ! ");
        }

        Duration rentalDuration = Duration.between(startRentTime, rentalEnd);
        long hours = rentalDuration.toHours();
        long days = rentalDuration.toDays();
        long weeks = days / 7;

        double priceSeats =  numberOfSeats * PRICE_PER_SEAT;

        double price = weeks * pricePerWeek + days * pricePerDay * fuelType.getPriceFuel() + hours * pricePerHour;

        return 5;
    }
}
