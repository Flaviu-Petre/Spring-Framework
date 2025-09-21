package com.glo.service;

import com.glo.client.Freight;
import com.glo.repository.FreightRepository;
import com.glo.repository.FreightRepositoryImpl;

import java.util.List;

public class FreightServiceImpl implements FreightService {
    private FreightRepository freightRepository;

    public FreightServiceImpl(FreightRepository freightRepository) {
        this.freightRepository = freightRepository;
    }

    @Override
    public void createFreight(Freight freight) {
        freightRepository.createFreight(freight);
    }

    @Override
    public Freight getFreightById(int orderId) {
        return freightRepository.getFreightById(orderId);
    }

    @Override
    public List<Freight> getAllFreight() {
        return freightRepository.getAllFreight();
    }

    @Override
    public void updateFreight(Freight freight) {
        freightRepository.updateFreight(freight);
    }

    @Override
    public void deleteFreight(int orderId) {
        Freight freight = freightRepository.getFreightById(orderId);
        if (freight != null) {
            freightRepository.deleteFreight(freight);
        }
    }

    public void setFreightServiceImpl(FreightRepositoryImpl freightServiceImpl) {
        this.freightRepository = freightServiceImpl;
    }
}
