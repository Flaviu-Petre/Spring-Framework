package com.glo.client;

import com.glo.service.FreightService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        System.out.println("=== Spring-based Fleet Management System ===\n");

        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-config.xml");

        try {
            System.out.println("1. Fetching Fleet bean from Spring context...");
            Fleet fleet = (Fleet) context.getBean("fleet");
            System.out.println("Fleet bean successfully retrieved from Spring context!\n");

            displayFleetDetails(fleet, "Initial Fleet Details (from XML configuration)");

            System.out.println("\n2. Setting additional properties using setter methods...");
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter new destination: ");
            String newDestination = scanner.nextLine();
            fleet.setDestination(newDestination);

            System.out.print("Enter new origin: ");
            String newOrigin = scanner.nextLine();
            fleet.setOrigin(newOrigin);

            System.out.print("Enter new number of fleets: ");
            int newNumberOfFleets = scanner.nextInt();
            fleet.setRequiredFleets(newNumberOfFleets);

            System.out.println("Properties updated successfully!\n");

            displayFleetDetails(fleet, "Updated Fleet Details");

            System.out.println("\n--- Creating Additional Fleet Instances ---");

            Fleet fleet2 = Fleet.createFleetInstance(2002, 10, "Chicago", "Miami");
            Fleet fleet3 = Fleet.createFleetInstance(3003, 8, "Seattle", "Denver");

            List<Fleet> fleets = new ArrayList<>();
            fleets.add(fleet);
            fleets.add(fleet2);
            fleets.add(fleet3);

            System.out.println("\n--- All Fleet Details ---");
            for (int i = 0; i < fleets.size(); i++) {
                displayFleetDetails(fleets.get(i), "Fleet " + (i + 1));
            }

            System.out.println("\n--- Demonstrating Bean Destruction ---");
            System.out.println("Closing Spring context to trigger destruction lifecycle methods...");

            scanner.close();

        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ((ClassPathXmlApplicationContext) context).close();
            System.out.println("Spring context closed. Application terminated.");
        }
    }
    private static void displayFleetDetails(Fleet fleet, String title) {
        System.out.println("=== " + title + " ===");
        System.out.println("Fleet ID: " + fleet.getFleetId());
        System.out.println("Number of Fleets: " + fleet.getRequiredFleets());
        System.out.println("Origin: " + fleet.getOrigin());
        System.out.println("Destination: " + fleet.getDestination());
        System.out.println("================================");
    }
}