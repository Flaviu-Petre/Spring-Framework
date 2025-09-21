package com.glo.client;

import com.glo.service.FreightService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    static FreightService freightService;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        freightService = context.getBean("freightService", FreightService.class);

        showMenu();
    }

    public static void showMenu() {
        while (true) {
            System.out.println("\n=== Freight & Fleet Management System ===");
            System.out.println("1. Create Freight Object manually");
            System.out.println("2. View all Freight objects");
            System.out.println("3. Update Freight object");
            System.out.println("4. Create Freight Object via Constructor Injection by Type");
            System.out.println("5. View Constructor Injection Examples");
            System.out.println("6. Display Fleet Management System"); // NEW OPTION FOR STEP 2
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    createFreightManually();
                    break;
                case 2:
                    viewAllFreight();
                    break;
                case 3:
                    updateFreight();
                    break;
                case 4:
                    createFreightViaConstructorInjectionByType();
                    break;
                case 5:
                    viewConstructorInjectionExamples();
                    break;
                case 6:
                    displayFleetManagementSystem(); // NEW METHOD FOR STEP 2
                    break;
                case 7:
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void displayFleetManagementSystem() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        System.out.println("\n=== STEP 2: Fleet Management System ===");
        System.out.println("Displaying number of fleets available using different injection methods:\n");

        List<Fleet> constructorFleets = new ArrayList<>();
        constructorFleets.add(context.getBean("fleetConstructorInjection", Fleet.class));
        constructorFleets.add(context.getBean("fleetWestZone", Fleet.class));

        List<Fleet> setterFleets = new ArrayList<>();
        setterFleets.add(context.getBean("fleetSetterInjection", Fleet.class));
        setterFleets.add(context.getBean("fleetEastZone", Fleet.class));

        System.out.println("FLEETS CREATED USING CONSTRUCTOR INJECTION:");
        System.out.println("Number of fleets: " + constructorFleets.size());
        for (int i = 0; i < constructorFleets.size(); i++) {
            Fleet fleet = constructorFleets.get(i);
            System.out.println("  " + (i + 1) + ". " + fleet);
            System.out.println("     Total Vehicles: " + fleet.getVehicleCount());
        }

        System.out.println();

        System.out.println("FLEETS CREATED USING SETTER INJECTION:");
        System.out.println("Number of fleets: " + setterFleets.size());
        for (int i = 0; i < setterFleets.size(); i++) {
            Fleet fleet = setterFleets.get(i);
            System.out.println("  " + (i + 1) + ". " + fleet);
            System.out.println("     Total Vehicles: " + fleet.getVehicleCount());
            if (fleet.getVehicleTypes() != null) {
                System.out.println("     Vehicle Types: " + fleet.getVehicleTypes());
            }
        }

        // Summary
        int totalFleets = constructorFleets.size() + setterFleets.size();
        int totalVehicles = constructorFleets.stream().mapToInt(Fleet::getVehicleCount).sum() +
                setterFleets.stream().mapToInt(Fleet::getVehicleCount).sum();

        System.out.println("Total number of fleets available: " + totalFleets);
        System.out.println("Total vehicles across all fleets: " + totalVehicles);
        System.out.println("Fleets using Constructor Injection: " + constructorFleets.size());
        System.out.println("Fleets using Setter Injection: " + setterFleets.size());
    }

    public static void createFreightManually() {
        System.out.print("Enter Order ID: ");
        int orderId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Origin: ");
        String origin = scanner.nextLine();

        System.out.print("Enter Destination: ");
        String destination = scanner.nextLine();

        Freight freight = new Freight(orderId, origin, destination);
        freightService.createFreight(freight);
        System.out.println("Freight created successfully: " + freight);
    }

    public static void createFreightViaConstructorInjectionByType() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        Freight freightByType = context.getBean("freightConstType", Freight.class);

        System.out.println("\n=== Option 4: Constructor Injection by Type ===");
        System.out.println("Creating Freight via Constructor Injection by Type...");
        System.out.println("Configuration used:");
        System.out.println("  <constructor-arg type=\"int\" value=\"103\"/>");
        System.out.println("  <constructor-arg type=\"java.lang.String\" value=\"Mumbai\"/>");
        System.out.println("  <constructor-arg type=\"java.lang.String\" value=\"Noida\"/>");
        System.out.println();
        System.out.println("Freight object created with injected values: " + freightByType);

        freightService.createFreight(freightByType);
        System.out.println("Freight object added to the system successfully!");
    }

    public static void viewConstructorInjectionExamples() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        System.out.println("\n=== All Constructor Injection Examples ===");

        Freight freightByIndex = context.getBean("freightConstIndex", Freight.class);
        System.out.println("1. Constructor Injection by Index: " + freightByIndex);

        Freight freightByName = context.getBean("freightConstName", Freight.class);
        System.out.println("2. Constructor Injection by Name: " + freightByName);

        Freight freightByType = context.getBean("freightConstType", Freight.class);
        System.out.println("3. Constructor Injection by Type: " + freightByType);

        Freight freightBySetter = context.getBean("freight", Freight.class);
        System.out.println("4. Setter Injection: " + freightBySetter);
    }

    public static void viewAllFreight() {
        System.out.println("\n=== All Freight Objects in System ===");
        List<Freight> freightList = freightService.getAllFreight();
        if (freightList.isEmpty()) {
            System.out.println("No freight objects found.");
        } else {
            for (int i = 0; i < freightList.size(); i++) {
                System.out.println((i + 1) + ". " + freightList.get(i));
            }
        }
    }

    public static void updateFreight() {
        System.out.print("Enter Order ID to update: ");
        int orderId = scanner.nextInt();
        scanner.nextLine();

        Freight existingFreight = freightService.getFreightById(orderId);
        if (existingFreight == null) {
            System.out.println("Freight with ID " + orderId + " not found.");
            return;
        }

        System.out.print("Enter new Origin (current: " + existingFreight.getOrigin() + "): ");
        String origin = scanner.nextLine();

        System.out.print("Enter new Destination (current: " + existingFreight.getDestination() + "): ");
        String destination = scanner.nextLine();

        Freight updatedFreight = new Freight(orderId, origin, destination);
        freightService.updateFreight(updatedFreight);
        System.out.println("Freight updated successfully: " + updatedFreight);
    }
}