package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;

public class Bicycle extends Vehicle {

    public Bicycle(String id, String model, double pricePerDay, double pricePerHour){
        super(id, model);
        super.pricePerDay = pricePerDay;
        super.pricePerHour = pricePerHour;
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        if(startOfRent.isAfter(endOfRent)){
            throw new InvalidRentingPeriodException("Invalid renting period ! ");
        }

        Duration rentalDuration = Duration.between(startRentTime, rentalEnd);
        if (rentalDuration.compareTo(Duration.ofDays(7)) >= 0) {
            throw new InvalidRentingPeriodException("Renting period is above one week!");
        }

        long totalDays = rentalDuration.toDays();
        long days = totalDays % 7;
        long hours = rentalDuration.toHours() % 24;

        return (days * pricePerDay) + (hours  * pricePerHour);
    }
}
