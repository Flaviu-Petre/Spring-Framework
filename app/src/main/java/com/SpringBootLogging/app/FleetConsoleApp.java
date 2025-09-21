package com.SpringBootLogging.app;

import com.SpringBootLogging.app.Entity.Fleet;
import com.SpringBootLogging.app.service.FleetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class FleetConsoleApp implements CommandLineRunner {

    @Autowired
    private FleetService fleetService;

    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.setProperty("spring.main.web-application-type", "none");
        SpringApplication.run(FleetConsoleApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=================================");
        System.out.println("   FLEET MANAGEMENT SYSTEM");
        System.out.println("=================================");

        boolean running = true;
        while (running) {
            running = showMenuAndProcessChoice();
        }

        System.out.println("Thank you for using Fleet Management System!");
        scanner.close();
    }

    private boolean showMenuAndProcessChoice() {
        displayMenu();

        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAllFleets();
                    break;
                case 2:
                    viewFleetById();
                    break;
                case 3:
                    addNewFleet();
                    break;
                case 4:
                    updateFleet();
                    break;
                case 5:
                    deleteFleet();
                    break;
                case 6:
                    searchFleets();
                    break;
                case 7:
                    showFleetCount();
                    break;
                case 8:
                    System.out.println("Exiting application...");
                    return false;
                default:
                    System.out.println("Invalid choice! Please select 1-8.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.nextLine(); 
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
        return true;
    }

    private void displayMenu() {
        System.out.println("\n=================================");
        System.out.println("           MAIN MENU");
        System.out.println("=================================");
        System.out.println("1. View All Fleets");
        System.out.println("2. View Fleet by ID");
        System.out.println("3. Add New Fleet");
        System.out.println("4. Update Fleet");
        System.out.println("5. Delete Fleet");
        System.out.println("6. Search Fleets");
        System.out.println("7. Show Fleet Count");
        System.out.println("8. Exit");
        System.out.println("=================================");
        System.out.print("Enter your choice (1-8): ");
    }

    private void viewAllFleets() {
        System.out.println("\n ALL FLEETS");
        System.out.println("==============");

        try {
            List<Fleet> fleets = fleetService.getAllFleets();
            if (fleets.isEmpty()) {
                System.out.println("No fleets found.");
                return;
            }

            System.out.printf("%-10s %-15s %-15s %-12s %-10s%n",
                    "ID", "Origin", "Destination", "No of Fleets", "Weight");
            System.out.println("----------------------------------------------------------------");

            for (Fleet fleet : fleets) {
                System.out.printf("%-10s %-15s %-15s %-12s %-10.2f%n",
                        fleet.getFleetId(),
                        fleet.getOrigin(),
                        fleet.getDestination(),
                        fleet.getNoOfFleets(),
                        fleet.getWeight());
            }
        } catch (Exception e) {
            System.out.println(" Error retrieving fleets: " + e.getMessage());
        }
    }

    private void viewFleetById() {
        System.out.println("\n🔍 VIEW FLEET BY ID");
        System.out.println("===================");

        try {
            System.out.print("Enter Fleet ID: ");
            Long fleetId = scanner.nextLong();
            scanner.nextLine();

            Fleet fleet = fleetService.getFleetById(fleetId);
            if (fleet != null) {
                displayFleetDetails(fleet);
            } else {
                System.out.println(" Fleet not found with ID: " + fleetId);
            }
        } catch (InputMismatchException e) {
            System.out.println(" Invalid input! Please enter a valid number.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println(" Error retrieving fleet: " + e.getMessage());
        }
    }

    private void addNewFleet() {
        System.out.println("\n➕ ADD NEW FLEET");
        System.out.println("=================");

        try {
            Fleet fleet = new Fleet();

            System.out.print("Enter Fleet ID: ");
            Long fleetId = scanner.nextLong();
            scanner.nextLine();
            fleet.setFleetId(fleetId);

            System.out.print("Enter Origin: ");
            String origin = scanner.nextLine().trim();
            if (origin.isEmpty()) {
                System.out.println(" Origin cannot be empty!");
                return;
            }
            fleet.setOrigin(origin);

            System.out.print("Enter Destination: ");
            String destination = scanner.nextLine().trim();
            if (destination.isEmpty()) {
                System.out.println(" Destination cannot be empty!");
                return;
            }
            fleet.setDestination(destination);

            System.out.print("Enter Number of Fleets: ");
            String noOfFleets = scanner.nextLine().trim();
            fleet.setNoOfFleets(noOfFleets);

            System.out.print("Enter Weight: ");
            double weight = scanner.nextDouble();
            scanner.nextLine();
            fleet.setWeight(weight);

            fleetService.createFleet(fleet);
            System.out.println(" Fleet created successfully!");

        } catch (InputMismatchException e) {
            System.out.println(" Invalid input! Please enter valid data.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println(" Error creating fleet: " + e.getMessage());
        }
    }

    private void updateFleet() {
        System.out.println("\n UPDATE FLEET");
        System.out.println("================");

        try {
            System.out.print("Enter Fleet ID to update: ");
            Long fleetId = scanner.nextLong();
            scanner.nextLine();

            Fleet existingFleet = fleetService.getFleetById(fleetId);
            if (existingFleet == null) {
                System.out.println(" Fleet not found with ID: " + fleetId);
                return;
            }

            System.out.println("Current fleet details:");
            displayFleetDetails(existingFleet);

            Fleet updatedFleet = new Fleet();
            updatedFleet.setFleetId(fleetId);

            System.out.print("Enter new Origin (current: " + existingFleet.getOrigin() + "): ");
            String origin = scanner.nextLine().trim();
            updatedFleet.setOrigin(origin.isEmpty() ? existingFleet.getOrigin() : origin);

            System.out.print("Enter new Destination (current: " + existingFleet.getDestination() + "): ");
            String destination = scanner.nextLine().trim();
            updatedFleet.setDestination(destination.isEmpty() ? existingFleet.getDestination() : destination);

            System.out.print("Enter new Number of Fleets (current: " + existingFleet.getNoOfFleets() + "): ");
            String noOfFleets = scanner.nextLine().trim();
            updatedFleet.setNoOfFleets(noOfFleets.isEmpty() ? existingFleet.getNoOfFleets() : noOfFleets);

            System.out.print("Enter new Weight (current: " + existingFleet.getWeight() + "): ");
            String weightInput = scanner.nextLine().trim();
            double weight = weightInput.isEmpty() ? existingFleet.getWeight() : Double.parseDouble(weightInput);
            updatedFleet.setWeight(weight);

            fleetService.updateFleet(fleetId, updatedFleet);
            System.out.println(" Fleet updated successfully!");

        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println(" Invalid input! Please enter valid data.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println(" Error updating fleet: " + e.getMessage());
        }
    }

    private void deleteFleet() {
        System.out.println("\n DELETE FLEET");
        System.out.println("================");

        try {
            System.out.print("Enter Fleet ID to delete: ");
            Long fleetId = scanner.nextLong();
            scanner.nextLine();

            Fleet existingFleet = fleetService.getFleetById(fleetId);
            if (existingFleet == null) {
                System.out.println(" Fleet not found with ID: " + fleetId);
                return;
            }

            System.out.println("Fleet to be deleted:");
            displayFleetDetails(existingFleet);

            System.out.print("Are you sure you want to delete this fleet? (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equals("y") || confirmation.equals("yes")) {
                fleetService.deleteFleet(fleetId);
                System.out.println(" Fleet deleted successfully!");
            } else {
                System.out.println(" Delete operation cancelled.");
            }

        } catch (InputMismatchException e) {
            System.out.println(" Invalid input! Please enter a valid number.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println(" Error deleting fleet: " + e.getMessage());
        }
    }

    private void searchFleets() {
        System.out.println("\n SEARCH FLEETS");
        System.out.println("=================");

        try {
            System.out.print("Enter origin to search (or press Enter to skip): ");
            String origin = scanner.nextLine().trim();

            System.out.print("Enter destination to search (or press Enter to skip): ");
            String destination = scanner.nextLine().trim();

            List<Fleet> allFleets = fleetService.getAllFleets();
            List<Fleet> filteredFleets = allFleets.stream()
                    .filter(fleet -> origin.isEmpty() ||
                            fleet.getOrigin().toLowerCase().contains(origin.toLowerCase()))
                    .filter(fleet -> destination.isEmpty() ||
                            fleet.getDestination().toLowerCase().contains(destination.toLowerCase()))
                    .toList();

            if (filteredFleets.isEmpty()) {
                System.out.println("No fleets found matching the search criteria.");
                return;
            }

            System.out.println("\nSearch Results (" + filteredFleets.size() + " found):");
            System.out.printf("%-10s %-15s %-15s %-12s %-10s%n",
                    "ID", "Origin", "Destination", "No of Fleets", "Weight");
            System.out.println("----------------------------------------------------------------");

            for (Fleet fleet : filteredFleets) {
                System.out.printf("%-10s %-15s %-15s %-12s %-10.2f%n",
                        fleet.getFleetId(),
                        fleet.getOrigin(),
                        fleet.getDestination(),
                        fleet.getNoOfFleets(),
                        fleet.getWeight());
            }
        } catch (Exception e) {
            System.out.println(" Error searching fleets: " + e.getMessage());
        }
    }

    private void showFleetCount() {
        System.out.println("\n FLEET COUNT");
        System.out.println("===============");

        try {
            List<Fleet> fleets = fleetService.getAllFleets();
            System.out.println("Total number of fleets: " + fleets.size());
        } catch (Exception e) {
            System.out.println(" Error getting fleet count: " + e.getMessage());
        }
    }

    private void displayFleetDetails(Fleet fleet) {
        System.out.println("\n--- Fleet Details ---");
        System.out.println("ID: " + fleet.getFleetId());
        System.out.println("Origin: " + fleet.getOrigin());
        System.out.println("Destination: " + fleet.getDestination());
        System.out.println("Number of Fleets: " + fleet.getNoOfFleets());
        System.out.println("Weight: " + fleet.getWeight());
        System.out.println("--------------------");
    }
}