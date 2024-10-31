package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.RentalService;
import bg.sofia.uni.fmi.mjt.vehiclerent.driver.AgeGroup;
import bg.sofia.uni.fmi.mjt.vehiclerent.driver.Driver;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleNotRentedException;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws InvalidRentingPeriodException {

        RentalService rentalService = new RentalService();
        LocalDateTime rentStart = LocalDateTime.of(2024, 10, 10, 0, 0, 0);
        Driver experiencedDriver = new Driver(AgeGroup.EXPERIENCED);

        Vehicle electricCar = new Car("1", "Tesla Model 3", FuelType.ELECTRICITY, 4, 1000, 150, 10);
        rentalService.rentVehicle(experiencedDriver, electricCar, rentStart);
        double priceToPay = rentalService.returnVehicle(electricCar, rentStart.plusDays(5)); // 770.0

        System.out.println("Car rental price: " + priceToPay);

        Vehicle dieselCar = new Car("2", "Toyota Auris", FuelType.DIESEL, 4, 500, 80, 5);
        rentalService.rentVehicle(experiencedDriver, dieselCar, rentStart);
        priceToPay = rentalService.returnVehicle(dieselCar, rentStart.plusDays(5)); // 80*5 + 3*5 + 4*5 = 435.0

        System.out.println("Car rental price: " + priceToPay);

//        try {
//            Vehicle bike = new Bicycle("3", "Bikey", 20, 6);
//            rentalService.rentVehicle(experiencedDriver, bike, rentStart);
//            priceToPay = rentalService.returnVehicle(bike, rentStart.plusDays(8)); // 80*5 + 3*5 + 4*5 = 435.0
//        }catch(VehicleNotRentedException e){
//            System.out.println(e.getMessage());
//        }catch (InvalidRentingPeriodException k){
//            System.out.println(k.getMessage());
//        }
//
        try {
            Vehicle caravana = new Caravan("C1", "Luxury Caravan", FuelType.DIESEL, 4, 2, 700, 100, 10);
            rentalService.rentVehicle(experiencedDriver, caravana, rentStart);
            priceToPay = rentalService.returnVehicle(caravana, rentStart.plusHours(8)); // 80*5 + 3*5 + 4*5 = 435.0
        }catch(VehicleNotRentedException e){
            System.out.println(e.getMessage());
        }catch (InvalidRentingPeriodException k){
            System.out.println(k.getMessage());
        }

        try {
            Vehicle caravana2 = new Caravan("d", "Luxury Caravan", FuelType.DIESEL, 4, 2, 700, 100, 10);
            //rentalService.rentVehicle(experiencedDriver, caravana, rentStart);
            priceToPay = rentalService.returnVehicle(caravana2, rentStart.plusDays(8)); // 80*5 + 3*5 + 4*5 = 435.0
        }catch(VehicleNotRentedException e){
            System.out.println(e.getMessage());
        }catch (InvalidRentingPeriodException k){
            System.out.println(k.getMessage());
        }




    }
}
