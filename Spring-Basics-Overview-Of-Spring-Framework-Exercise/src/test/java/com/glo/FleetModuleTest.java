package com.glo;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FleetModuleTest extends TestCase {
    @Test
    public void testSpringContextLoads() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        assertNotNull("Spring context should load", context);
        ((ClassPathXmlApplicationContext) context).close();
    }

    @Test
    public void testFleetBeanInstantiation() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        Fleet fleet1 = context.getBean("fleet1", Fleet.class);
        assertNotNull("Fleet1 should be instantiated", fleet1);
        assertEquals("Fleet ID should be 1001", 1001, fleet1.getFleetId());

        ((ClassPathXmlApplicationContext) context).close();
    }

    @Test
    public void testClientBeanInstantiation() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        Client client1 = context.getBean("client1", Client.class);
        assertNotNull("Client1 should be instantiated", client1);
        assertEquals("Client ID should be 2001", 2001, client1.getClientId());
        assertEquals("Client name should match", "Global Manufacturing Corp", client1.getClientName());

        ((ClassPathXmlApplicationContext) context).close();
    }
}