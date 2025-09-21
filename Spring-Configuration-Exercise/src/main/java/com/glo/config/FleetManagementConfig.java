package com.glo.config;

import com.glo.client.Client;
import com.glo.client.Fleet;
import com.glo.repository.FleetRepositoryImpl;
import com.glo.repository.FreightRepositoryImpl;
import com.glo.repository.FastFreightRepositoryImpl;
import com.glo.repository.Interface.FleetRepository;
import com.glo.repository.Interface.FreightRepository;
import com.glo.service.FleetServiceImpl;
import com.glo.service.FreightServiceImpl;
import com.glo.service.FastFreightServiceImpl;
import com.glo.service.Interface.FleetService;
import com.glo.service.Interface.FreightService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;

/**
 * Java Configuration class demonstrating Spring bean definition
 * Alternative to XML configuration for defining beans
 */
@Configuration
public class FleetManagementConfig {

    // ====================== REPOSITORY BEANS ======================

    @Bean
    @Primary
    public FreightRepository freightRepository() {
        FreightRepositoryImpl repository = new FreightRepositoryImpl();
        repository.setFreightList(new ArrayList<>());
        System.out.println("Java Config: FreightRepository bean created");
        return repository;
    }

    @Bean
    @Qualifier("FastFreightRepositoryImpl")
    public FreightRepository FastFreightRepositoryImpl() {
        FastFreightRepositoryImpl repository = new FastFreightRepositoryImpl();
        repository.setFreightList(new ArrayList<>());
        System.out.println("Java Config: FastFreightRepositoryImpl bean created");
        return repository;
    }

    @Bean
    public FleetRepository fleetRepository() {
        FleetRepositoryImpl repository = new FleetRepositoryImpl(new ArrayList<>());
        System.out.println("Java Config: FleetRepository bean created");
        return repository;
    }

    // ====================== SERVICE BEANS ======================

    @Bean
    @Primary
    public FreightService freightService(FreightRepository freightRepository) {
        System.out.println("Java Config: FreightService bean created with dependency injection");
        return new FreightServiceImpl(freightRepository);
    }

    @Bean
    @Qualifier("fastFreightService")
    public FreightService fastFreightService(@Qualifier("FastFreightRepositoryImpl") FreightRepository FastFreightRepositoryImpl) {
        System.out.println("Java Config: FastFreightService bean created with @Qualifier injection");
        return new FastFreightServiceImpl(FastFreightRepositoryImpl);
    }

    @Bean
    public FleetService fleetService(FleetRepository fleetRepository) {
        System.out.println("Java Config: FleetService bean created with dependency injection");
        return new FleetServiceImpl(fleetRepository);
    }

    // ====================== FLEET BEANS ======================

    @Bean
    public Fleet fleet() {
        // Using factory method as defined in the original requirements
        Fleet fleet = Fleet.createFleetInstance(2001, 8, "Boston", "Seattle");
        System.out.println("Java Config: Fleet bean created using factory method");
        return fleet;
    }

    // ====================== CLIENT BEANS - DEMONSTRATING VARIOUS INJECTION TYPES ======================

    /**
     * Client bean demonstrating constructor injection
     */
    @Bean
    public Client clientWithJavaConfigConstructor(@Qualifier("fastFreightService") FreightService fastFreightService) {
        System.out.println("Java Config: Creating Client with constructor injection");
        return new Client(180.5, fastFreightService);
    }

    /**
     * Client bean demonstrating setter injection via method calls
     */
    @Bean
    public Client clientWithJavaConfigSetter(FreightService freightService) {
        System.out.println("Java Config: Creating Client with setter injection");
        Client client = new Client();
        client.setWeight(225.75);
        client.setFreightService(freightService);
        return client;
    }

    /**
     * Client bean demonstrating mixed injection - constructor + setter
     */
    @Bean
    public Client clientWithJavaConfigMixed(FreightService freightService,
                                            @Qualifier("fastFreightService") FreightService fastFreightService) {
        System.out.println("Java Config: Creating Client with mixed injection");
        Client client = new Client(300.0); // Constructor injection for weight
        client.setFreightService(freightService); // Setter injection for regular service
        client.setFastFreightService(fastFreightService); // Setter injection for fast service
        return client;
    }
}