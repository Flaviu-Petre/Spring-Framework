package com.AOP.app;

import com.AOP.app.client.Freight;
import com.AOP.app.service.FreightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class AppApplication implements CommandLineRunner {

    @Autowired
    private FreightService freightService;

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("========================================");
        System.out.println("    Welcome to Freight Management System");
        System.out.println("========================================");

        while (running) {
            displayMenu();
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        viewAllFreights();
                        break;
                    case 2:
                        addNewFreight(scanner);
                        break;
                    case 3:
                        updateExistingFreight(scanner);
                        break;
                    case 4:
                        deleteFreight(scanner);
                        break;
                    case 5:
                        searchFreightById(scanner);
                        break;
                    case 6:
                        System.out.println("Thank you for using Freight Management System!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice! Please enter a number between 1-6.");
                }

                if (running) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    clearScreen();
                }

            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }
        }

        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║           FREIGHT MANAGEMENT          ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║  1. View All Freights                 ║");
        System.out.println("║  2. Add New Freight                    ║");
        System.out.println("║  3. Update Freight                     ║");
        System.out.println("║  4. Delete Freight                     ║");
        System.out.println("║  5. Search Freight by ID               ║");
        System.out.println("║  6. Exit Application                   ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    private void viewAllFreights() {
        System.out.println("\n=== ALL FREIGHTS ===");
        List<Freight> freights = freightService.getAllFreight();

        if (freights == null || freights.isEmpty()) {
            System.out.println("No freights found in the system.");
            return;
        }

        System.out.printf("%-10s %-15s %-15s%n", "Order ID", "Origin", "Destination");
        System.out.println("------------------------------------------");

        for (Freight freight : freights) {
            System.out.printf("%-10d %-15s %-15s%n",
                    freight.getOrderId(),
                    freight.getOrigin(),
                    freight.getDestination());
        }

        System.out.println("\nTotal freights: " + freights.size());
    }

    private void addNewFreight(Scanner scanner) {
        System.out.println("\n=== ADD NEW FREIGHT ===");

        try {
            System.out.print("Enter Order ID: ");
            int orderId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Check if freight with this ID already exists
            Freight existingFreight = freightService.getFreightById(orderId);
            if (existingFreight != null) {
                System.out.println("Error: Freight with Order ID " + orderId + " already exists!");
                return;
            }

            System.out.print("Enter Origin: ");
            String origin = scanner.nextLine().trim();

            System.out.print("Enter Destination: ");
            String destination = scanner.nextLine().trim();

            if (origin.isEmpty() || destination.isEmpty()) {
                System.out.println("Error: Origin and Destination cannot be empty!");
                return;
            }

            Freight newFreight = new Freight(orderId, origin, destination);
            freightService.createFreight(newFreight);

            System.out.println("✓ Freight added successfully!");
            System.out.println("Details: " + newFreight);

        } catch (Exception e) {
            System.out.println("Error: Invalid input format!");
            scanner.nextLine(); // Clear invalid input
        }
    }

    private void updateExistingFreight(Scanner scanner) {
        System.out.println("\n=== UPDATE FREIGHT ===");

        try {
            System.out.print("Enter Order ID to update: ");
            int orderId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Freight existingFreight = freightService.getFreightById(orderId);
            if (existingFreight == null) {
                System.out.println("Error: Freight with Order ID " + orderId + " not found!");
                return;
            }

            System.out.println("Current Details: " + existingFreight);
            System.out.println("\nEnter new details (press Enter to keep current value):");

            System.out.print("New Origin [" + existingFreight.getOrigin() + "]: ");
            String newOrigin = scanner.nextLine().trim();
            if (newOrigin.isEmpty()) {
                newOrigin = existingFreight.getOrigin();
            }

            System.out.print("New Destination [" + existingFreight.getDestination() + "]: ");
            String newDestination = scanner.nextLine().trim();
            if (newDestination.isEmpty()) {
                newDestination = existingFreight.getDestination();
            }

            Freight updatedFreight = new Freight(orderId, newOrigin, newDestination);
            freightService.updateFreight(updatedFreight);

            System.out.println("✓ Freight updated successfully!");
            System.out.println("Updated Details: " + updatedFreight);

        } catch (Exception e) {
            System.out.println("Error: Invalid input format!");
            scanner.nextLine(); // Clear invalid input
        }
    }

    private void deleteFreight(Scanner scanner) {
        System.out.println("\n=== DELETE FREIGHT ===");

        try {
            System.out.print("Enter Order ID to delete: ");
            int orderId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Freight existingFreight = freightService.getFreightById(orderId);
            if (existingFreight == null) {
                System.out.println("Error: Freight with Order ID " + orderId + " not found!");
                return;
            }

            System.out.println("Freight to delete: " + existingFreight);
            System.out.print("Are you sure you want to delete this freight? (y/N): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equals("y") || confirmation.equals("yes")) {
                freightService.deleteFreight(orderId);
                System.out.println("✓ Freight deleted successfully!");
            } else {
                System.out.println("Delete operation cancelled.");
            }

        } catch (Exception e) {
            System.out.println("Error: Invalid input format!");
            scanner.nextLine(); // Clear invalid input
        }
    }

    private void searchFreightById(Scanner scanner) {
        System.out.println("\n=== SEARCH FREIGHT ===");

        try {
            System.out.print("Enter Order ID to search: ");
            int orderId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Freight freight = freightService.getFreightById(orderId);
            if (freight != null) {
                System.out.println("✓ Freight found!");
                System.out.printf("%-10s %-15s %-15s%n", "Order ID", "Origin", "Destination");
                System.out.println("------------------------------------------");
                System.out.printf("%-10d %-15s %-15s%n",
                        freight.getOrderId(),
                        freight.getOrigin(),
                        freight.getDestination());
            } else {
                System.out.println("✗ Freight with Order ID " + orderId + " not found!");
            }

        } catch (Exception e) {
            System.out.println("Error: Invalid input format!");
            scanner.nextLine(); // Clear invalid input
        }
    }

    private void clearScreen() {
        // Simple way to clear screen - print multiple newlines
        for (int i = 0; i < 2; i++) {
            System.out.println();
        }
    }
}