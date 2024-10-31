package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;

public class Bicycle extends Vehicle {

    private double pricePerDay;
    private double pricePerHour;


    public Bicycle(String id, String model, double pricePerDay, double pricePerHour){
        super(id, model);
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        if(startOfRent.isAfter(endOfRent)){
            throw new InvalidRentingPeriodException("Invalid renting period ! ");
        }

        Duration rentalDuration = Duration.between(startRentTime, rentalEnd);
        long hours = rentalDuration.toHours();
        long days = rentalDuration.toDays();

        return (days * pricePerDay) + ((hours % 24) * pricePerHour);
    }
}
