package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.driver.Driver;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleAlreadyRentedException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleNotRentedException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Vehicle {

    protected String id;
    protected String model;
    protected Driver driver;
    protected LocalDateTime startRentTime;
    protected LocalDateTime rentalEnd;
    protected double pricePerDay;
    protected double pricePerHour;

    protected boolean isRented = false;

    public Vehicle(String id, String model) {
        this.id = id;
        this.model = model;
    }

    public LocalDateTime getStartRentTime() {
        return startRentTime;
    }

    public LocalDateTime getRentalEnd() {
        return rentalEnd;
    }

    public boolean getIsRented(){
        return isRented;
    }

    /**
     * Simulates rental of the vehicle. The vehicle now is considered rented by the provided driver and the start of the rental is the provided date.
     *
     * @param driver        the driver that wants to rent the vehicle.
     * @param startRentTime the start time of the rent
     * @throws VehicleAlreadyRentedException in case the vehicle is already rented by someone else or by the same driver.
     */
    public void rent(Driver driver, LocalDateTime startRentTime) {
        if (isRented) {
            throw new VehicleAlreadyRentedException("The vehicle is already rented!");
        }

        this.driver = driver;
        this.startRentTime = startRentTime;
        isRented = true;
    }

    /**
     * Simulates end of rental for the vehicle - it is no longer rented by a driver.
     *
     * @param rentalEnd time of end of rental
     * @throws IllegalArgumentException      in case @rentalEnd is null
     * @throws VehicleNotRentedException     in case the vehicle is not rented at all
     * @throws InvalidRentingPeriodException in case the rentalEnd is before the currently noted start date of rental or
     *                                       in case the Vehicle does not allow the passed period for rental, e.g. Caravans must be rented for at least a day
     *                                       and the driver tries to return them after an hour.
     */

    private void checkReturnBackTime(LocalDateTime rentalEnd) throws InvalidRentingPeriodException {
        if (rentalEnd == null) {
            throw new IllegalArgumentException("Value of return time is null");
        }

        if (!isRented) {
            throw new VehicleNotRentedException("Vehicle is not rented to be returned !");
        }

        if (startRentTime.isAfter(rentalEnd)) {
            throw new InvalidRentingPeriodException("Invalid renting period!");
        }
    }

    public void returnBack(LocalDateTime rentalEnd) throws InvalidRentingPeriodException {
        checkReturnBackTime(rentalEnd);

        this.rentalEnd = rentalEnd;
        isRented = false;
    }

    /**
     * Used to calculate potential rental price without the vehicle to be rented.
     * The calculation is based on the type of the Vehicle (Car/Caravan/Bicycle).
     *
     * @param startOfRent the beginning of the rental
     * @param endOfRent   the end of the rental
     * @return potential price for rent
     * @throws InvalidRentingPeriodException in case the vehicle cannot be rented for that period of time or
     *                                       the period is not valid (end date is before start date)
     */
    public abstract double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException;


}
