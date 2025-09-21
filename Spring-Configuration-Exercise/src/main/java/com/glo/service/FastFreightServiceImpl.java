package com.glo.service;

import com.glo.client.Freight;
import com.glo.repository.Interface.FreightRepository;
import com.glo.service.Interface.FreightService;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class FastFreightServiceImpl implements FreightService {
    private FreightRepository freightRepository;

    public FastFreightServiceImpl(@Qualifier("fastFreightRepository") FreightRepository freightRepository) {
        this.freightRepository = freightRepository;
        System.out.println("FastFreightServiceImpl: Constructor injection completed with FastFreightRepository");
    }

    @Override
    public void createFreight(Freight freight) {
        System.out.println("FastFreightServiceImpl: Processing express freight creation");
        freightRepository.createFreight(freight);
    }

    @Override
    public Freight getFreightById(int orderId) {
        System.out.println("FastFreightServiceImpl: Fast lookup for freight ID: " + orderId);
        return freightRepository.getFreightById(orderId);
    }

    @Override
    public List<Freight> getAllFreight() {
        System.out.println("FastFreightServiceImpl: Retrieving all freight with express processing");
        return freightRepository.getAllFreight();
    }

    @Override
    public void updateFreight(Freight freight) {
        System.out.println("FastFreightServiceImpl: Priority update for freight: " + freight.getOrderId());
        freightRepository.updateFreight(freight);
    }

    @Override
    public void deleteFreight(int orderId) {
        Freight freight = freightRepository.getFreightById(orderId);
        if (freight != null) {
            System.out.println("FastFreightServiceImpl: Express deletion for freight: " + orderId);
            freightRepository.deleteFreight(freight);
        }
    }

    public FreightRepository getFreightRepository() {
        return freightRepository;
    }

    public void setFreightRepository(FreightRepository freightRepository) {
        this.freightRepository = freightRepository;
        System.out.println("FastFreightServiceImpl: Setter injection completed");
    }
}
