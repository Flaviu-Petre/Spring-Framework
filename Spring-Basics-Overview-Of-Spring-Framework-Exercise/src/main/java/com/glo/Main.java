package com.glo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        Fleet fleet1 = (Fleet) context.getBean("fleet1", Fleet.class);
        System.out.println(fleet1.getFleetId());

        ApplicationContext filecontext = new FileSystemXmlApplicationContext("src/main/resources/spring-config.xml");
        Fleet fleet2 = (Fleet) filecontext.getBean("fleet2", Fleet.class);
        System.out.println(fleet2.getFleetId());
    }

    private static void demonstrateFleetManagement(ApplicationContext context) {
        System.out.println("--- Fleet Management Demo ---");

        Fleet fleet1 = context.getBean("fleet1", Fleet.class);
        Fleet fleet2 = context.getBean("fleet2", Fleet.class);
        Fleet fleet3 = context.getBean("fleet3", Fleet.class);

        System.out.println("Fleet objects instantiated via Spring DI:");
        System.out.println("  " + fleet1);
        System.out.println("  " + fleet2);
        System.out.println("  " + fleet3);
        System.out.println();
    }

    private static void demonstrateClientManagement(ApplicationContext context) {
        System.out.println("--- Client Management Demo ---");

        Client client1 = context.getBean("client1", Client.class);
        Client client2 = context.getBean("client2", Client.class);
        Client client3 = context.getBean("client3", Client.class);
        Client client4 = context.getBean("client4", Client.class);

        System.out.println("Client objects instantiated via Spring DI:");
        System.out.println("  " + client1);
        System.out.println("  " + client2);
        System.out.println("  " + client3);
        System.out.println("  " + client4);
        System.out.println();
    }

    private static void demonstrateIntegration(ApplicationContext context) {
        System.out.println("--- Fleet-Client Integration Demo ---");

        Fleet mainFleet = context.getBean("fleet1", Fleet.class);
        Client priorityClient = context.getBean("client1", Client.class);

        System.out.println("Assigning client to fleet:");
        System.out.println("Client: " + priorityClient.getClientName());
        System.out.println("Assignment completed using Spring-managed objects");
    }
}