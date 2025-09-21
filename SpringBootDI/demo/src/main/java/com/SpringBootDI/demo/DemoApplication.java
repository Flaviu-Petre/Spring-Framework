package com.SpringBootDI.demo;

import com.SpringBootDI.demo.Entity.Vehicle;
import com.SpringBootDI.demo.Service.FleetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class DemoApplication {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private FleetService fleetService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner testPrototypePattern() {
        return args -> {
            System.out.println("=================================================");
            System.out.println("    FLEET MANAGEMENT SYSTEM - PROTOTYPE TEST");
            System.out.println("=================================================");

            // Test 1: Demonstrate Prototype Pattern
            demonstratePrototypePattern();

            // Test 2: Test Fleet Management Operations
            demonstrateFleetOperations();

            System.out.println("=================================================");
            System.out.println("              TESTS COMPLETED");
            System.out.println("=================================================");
        };
    }

    private void demonstratePrototypePattern() {
        System.out.println("\n TESTING PROTOTYPE PATTERN");
        System.out.println("==============================");

        // Create multiple Vehicle instances from Spring context
        Vehicle vehicle1 = applicationContext.getBean(Vehicle.class);
        Vehicle vehicle2 = applicationContext.getBean(Vehicle.class);
        Vehicle vehicle3 = applicationContext.getBean(Vehicle.class);

        // Set different properties to each instance
        vehicle1.setVehicleId(1);
        vehicle1.setName("Toyota");
        vehicle1.setModel("Camry");
        vehicle1.setYear(2022);
        vehicle1.setMileage(25.5);

        vehicle2.setVehicleId(2);
        vehicle2.setName("Honda");
        vehicle2.setModel("Civic");
        vehicle2.setYear(2023);
        vehicle2.setMileage(28.0);

        vehicle3.setVehicleId(3);
        vehicle3.setName("Ford");
        vehicle3.setModel("F-150");
        vehicle3.setYear(2021);
        vehicle3.setMileage(18.5);

        // Verify that each instance is unique
        System.out.println("Vehicle 1 Hash Code: " + vehicle1.hashCode());
        System.out.println("Vehicle 2 Hash Code: " + vehicle2.hashCode());
        System.out.println("Vehicle 3 Hash Code: " + vehicle3.hashCode());

        System.out.println("\n Prototype Pattern Verification:");
        System.out.println("vehicle1 == vehicle2: " + (vehicle1 == vehicle2));
        System.out.println("vehicle2 == vehicle3: " + (vehicle2 == vehicle3));
        System.out.println("vehicle1 == vehicle3: " + (vehicle1 == vehicle3));

        if (vehicle1 != vehicle2 && vehicle2 != vehicle3 && vehicle1 != vehicle3) {
            System.out.println(" SUCCESS: Each Vehicle instance is unique (Prototype pattern working!)");
        } else {
            System.out.println(" FAILURE: Instances are not unique");
        }

        System.out.println("\n Vehicle Details:");
        System.out.println("Vehicle 1: " + vehicle1);
        System.out.println("Vehicle 2: " + vehicle2);
        System.out.println("Vehicle 3: " + vehicle3);
    }

    private void demonstrateFleetOperations() {
        System.out.println("\n TESTING FLEET MANAGEMENT OPERATIONS");
        System.out.println("=======================================");

        // Test adding vehicles
        System.out.println("\n➕ Adding vehicles to fleet...");
        fleetService.addVehicle("BMW", "X5", 2023, 22.0);
        fleetService.addVehicle("Mercedes", "C-Class", 2022, 24.5);
        fleetService.addVehicle("Audi", "A4", 2023, 26.0);
        System.out.println(" 3 vehicles added to fleet");

        // Test retrieving vehicles
        System.out.println("\n Retrieving vehicles from fleet...");
        Vehicle retrievedVehicle1 = fleetService.getVehicle(1);
        Vehicle retrievedVehicle2 = fleetService.getVehicle(2);
        Vehicle retrievedVehicle3 = fleetService.getVehicle(3);

        System.out.println("Vehicle 1: " + retrievedVehicle1);
        System.out.println("Vehicle 2: " + retrievedVehicle2);
        System.out.println("Vehicle 3: " + retrievedVehicle3);

        // Test updating a vehicle
        System.out.println("\n Updating vehicle with ID 2...");
        fleetService.updateVehicle(2, "Mercedes-Benz", "E-Class", 2023, 23.0);
        Vehicle updatedVehicle = fleetService.getVehicle(2);
        System.out.println("Updated Vehicle 2: " + updatedVehicle);

        // Test removing a vehicle
        System.out.println("\n Removing vehicle with ID 3...");
        fleetService.removeVehicle(3);
        Vehicle removedVehicle = fleetService.getVehicle(3);
        System.out.println("Attempting to retrieve removed vehicle: " +
                (removedVehicle == null ? "NULL (successfully removed)" : removedVehicle));

        // Verify remaining vehicles
        System.out.println("\n Remaining vehicles in fleet:");
        Vehicle remainingVehicle1 = fleetService.getVehicle(1);
        Vehicle remainingVehicle2 = fleetService.getVehicle(2);

        System.out.println("Vehicle 1: " + remainingVehicle1);
        System.out.println("Vehicle 2: " + remainingVehicle2);

        // Additional prototype test with service layer
        System.out.println("\n Testing prototype pattern through service layer...");
        testPrototypeThroughService();
    }

    private void testPrototypeThroughService() {
        // Create vehicles with same data but should be different instances
        fleetService.addVehicle("Tesla", "Model S", 2023, 120.0);
        fleetService.addVehicle("Tesla", "Model S", 2023, 120.0);

        // These should be different Vehicle instances even with same data
        Vehicle tesla1 = fleetService.getVehicle(4); // Assuming IDs 4 and 5
        Vehicle tesla2 = fleetService.getVehicle(5);

        if (tesla1 != null && tesla2 != null) {
            System.out.println("Tesla 1 Hash: " + tesla1.hashCode());
            System.out.println("Tesla 2 Hash: " + tesla2.hashCode());
            System.out.println("Different instances with same data: " + (tesla1 != tesla2));
            System.out.println("Tesla 1: " + tesla1);
            System.out.println("Tesla 2: " + tesla2);
        }
    }
}