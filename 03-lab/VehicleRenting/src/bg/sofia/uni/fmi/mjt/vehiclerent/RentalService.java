package bg.sofia.uni.fmi.mjt.vehiclerent;

import bg.sofia.uni.fmi.mjt.vehiclerent.driver.Driver;
//bc we use all the exceptions
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.*;
import bg.sofia.uni.fmi.mjt.vehiclerent.vehicle.Vehicle;

import java.time.Duration;
import java.time.LocalDateTime;

public class RentalService {

    /**
     * Simulates renting of the vehicle. Makes all required validations and then the provided driver "rents" the provided
     * vehicle with start time @startOfRent
     *
     * @param driver      the designated driver of the vehicle
     * @param vehicle     the chosen vehicle to be rented
     * @param startOfRent the start time of the rental
     * @throws IllegalArgumentException      if any of the passed arguments are null
     * @throws VehicleAlreadyRentedException in case the provided vehicle is already rented
     */
    public void rentVehicle(Driver driver, Vehicle vehicle, LocalDateTime startOfRent) {
       if(driver == null || vehicle == null || startOfRent == null){
           throw new IllegalArgumentException("Passed arguments are null");
       }
       if(vehicle.getIsRented()){
           throw new VehicleAlreadyRentedException("Vehicle already rented!");
       }
        try {
            vehicle.rent(driver, startOfRent);
        } catch (VehicleAlreadyRentedException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method simulates rental return - it includes validation of the arguments that throw the listed exceptions
     * in case of errors. The method returns the expected total price for the rental - price for the vehicle plus
     * additional tax for the driver, if it is applicable
     *
     * @param vehicle   the rented vehicle
     * @param endOfRent the end time of the rental
     * @return price for the rental
     * @throws IllegalArgumentException      in case @endOfRent or @vehicle is null
     * @throws VehicleNotRentedException     in case the vehicle is not rented at all
     * @throws InvalidRentingPeriodException in case the endOfRent is before the start of rental, or the vehicle
     *                                       does not allow the passed period for rental, e.g. Caravans must be rented for at least a day.
     */
    public double returnVehicle(Vehicle vehicle, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        if(endOfRent == null || vehicle == null){
            throw new IllegalArgumentException("Passed arguments are null");
        }

        if(!vehicle.getIsRented()){
            throw new VehicleNotRentedException("Vehicle is not rented!!");
        }

        if(endOfRent.isBefore(vehicle.getStartRentTime())){
            throw new InvalidRentingPeriodException("EndOfRent is before the start of rental");
        }

        try {
            vehicle.returnBack(endOfRent);
            return vehicle.calculateRentalPrice(vehicle.getStartRentTime(), vehicle.getRentalEnd());
        } catch (VehicleNotRentedException err) {
            throw new VehicleNotRentedException("err", err);}
//        }catch(InvalidRentingPeriodException e){
//            System.out.println(e.getMessage());
//            throw new InvalidRentingPeriodException("Ivalid period", e);
//        }
    }
}
