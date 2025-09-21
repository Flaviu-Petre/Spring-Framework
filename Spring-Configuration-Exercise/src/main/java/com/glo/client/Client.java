package com.glo.client;

import com.glo.service.Interface.FreightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    // Weight attribute as required
    private double weight;

    // Demonstrate @Autowired injection - will be injected via annotation
    @Autowired
    @Qualifier("freightService")
    private FreightService freightService;

    private FreightService fastFreightService;

    //region Constructors

    public Client() {
        this.weight = 100.0; // Default weight initialized through constructor
        System.out.println("Client: Default constructor called, weight initialized to: " + this.weight);
    }

    public Client(double weight) {
        this.weight = weight;
        System.out.println("Client: Weight constructor called, weight set to: " + this.weight);
    }

    // Constructor for demonstrating constructor injection with FastFreightService
    public Client(double weight, @Qualifier("fastFreightService") FreightService fastFreightService) {
        this.weight = weight;
        this.fastFreightService = fastFreightService;
        System.out.println("Client: Constructor injection completed - weight: " + weight +
                ", FastFreightService injected: " + (fastFreightService != null));
    }

    //endregion

    //region Getter and Setter methods for weight (demonstrating setter injection)

    public double getWeight() {
        return weight;
    }

    // Setter injection - weight will be initialized through setter
    public void setWeight(double weight) {
        this.weight = weight;
        System.out.println("Client: Weight set through setter injection: " + this.weight);
    }

    //endregion

    //region Getter and Setter for FreightService (demonstrating setter injection)

    public FreightService getFreightService() {
        return freightService;
    }

    // Setter injection for FreightService
    public void setFreightService(FreightService freightService) {
        this.freightService = freightService;
        System.out.println("Client: FreightService set through setter injection");
    }

    //endregion

    //region Getter and Setter for FastFreightService

    public FreightService getFastFreightService() {
        return fastFreightService;
    }

    public void setFastFreightService(FreightService fastFreightService) {
        this.fastFreightService = fastFreightService;
        System.out.println("Client: FastFreightService set through setter injection");
    }

    //endregion

    public static void main(String[] args) {
        System.out.println("=== Fleet Management System with Diverse Autowiring Modes ===\n");

        // Load Spring ApplicationContext from XML configuration
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-config.xml");

        try {
            // 1. Demonstrate Fleet bean creation and lifecycle
            System.out.println("1. === Fleet Bean Demonstration ===");
            Fleet fleet = (Fleet) context.getBean("fleet");
            displayFleetDetails(fleet, "Fleet from Spring Context");

            // 2. Demonstrate Client beans with different autowiring modes
            System.out.println("\n2. === Client Bean Autowiring Demonstrations ===");

            // Client with @Autowired annotation injection
            System.out.println("\n--- Client with @Autowired Annotation ---");
            Client clientWithAutowired = (Client) context.getBean("clientWithAutowired");
            System.out.println("Weight: " + clientWithAutowired.getWeight());
            System.out.println("FreightService injected via @Autowired: " +
                    (clientWithAutowired.getFreightService() != null));

            // Client with constructor injection
            System.out.println("\n--- Client with Constructor Injection ---");
            Client clientWithConstructor = (Client) context.getBean("clientWithConstructor");
            System.out.println("Weight: " + clientWithConstructor.getWeight());
            System.out.println("FastFreightService injected via Constructor: " +
                    (clientWithConstructor.getFastFreightService() != null));

            // Client with setter injection
            System.out.println("\n--- Client with Setter Injection ---");
            Client clientWithSetter = (Client) context.getBean("clientWithSetter");
            System.out.println("Weight: " + clientWithSetter.getWeight());
            System.out.println("FreightService injected via Setter: " +
                    (clientWithSetter.getFreightService() != null));

            // 3. Demonstrate service functionality
            System.out.println("\n3. === Service Functionality Demonstration ===");

            // Test regular FreightService
            System.out.println("\n--- Regular FreightService Operations ---");
            FreightService regularService = clientWithAutowired.getFreightService();
            testFreightService(regularService, "Regular");

            // Test FastFreightService
            System.out.println("\n--- Fast FreightService Operations ---");
            FreightService fastService = clientWithConstructor.getFastFreightService();
            testFreightService(fastService, "Fast");

            // 4. Interactive fleet management
            interactiveFleetManagement(fleet);

        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the Spring context - this will trigger destroy methods
            System.out.println("\n=== Bean Destruction Demonstration ===");
            System.out.println("Closing Spring context to trigger destruction lifecycle methods...");
            ((ClassPathXmlApplicationContext) context).close();
            System.out.println("Spring context closed. Application terminated.");
        }
    }

    private static void testFreightService(FreightService service, String serviceType) {
        if (service != null) {
            // Create test freight
            Freight freight1 = new Freight(1001, "New York", "Los Angeles");
            Freight freight2 = new Freight(1002, "Chicago", "Miami");

            // Test service operations
            service.createFreight(freight1);
            service.createFreight(freight2);

            // Display all freight
            List<Freight> allFreight = service.getAllFreight();
            System.out.println(serviceType + " Service - All Freight orders:");
            for (Freight freight : allFreight) {
                System.out.println("  " + freight);
            }
        }
    }

    private static void interactiveFleetManagement(Fleet fleet) {
        System.out.println("\n4. === Interactive Fleet Management ===");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter new destination for fleet: ");
        String newDestination = scanner.nextLine();
        fleet.setDestination(newDestination);

        System.out.print("Enter new origin for fleet: ");
        String newOrigin = scanner.nextLine();
        fleet.setOrigin(newOrigin);

        System.out.print("Enter new number of required fleets: ");
        int newRequiredFleets = scanner.nextInt();
        fleet.setRequiredFleets(newRequiredFleets);

        System.out.println("Fleet updated successfully!");
        displayFleetDetails(fleet, "Updated Fleet Details");

        scanner.close();
    }

    private static void displayFleetDetails(Fleet fleet, String title) {
        System.out.println("=== " + title + " ===");
        System.out.println("Fleet ID: " + fleet.getFleetId());
        System.out.println("Required Fleets: " + fleet.getRequiredFleets());
        System.out.println("Origin: " + fleet.getOrigin());
        System.out.println("Destination: " + fleet.getDestination());
        System.out.println("================================");
    }
}