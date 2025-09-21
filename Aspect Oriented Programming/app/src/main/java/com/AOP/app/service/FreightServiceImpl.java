package com.AOP.app.service;

import com.AOP.app.client.Freight;
import com.AOP.app.repository.FreightRepository;
import com.AOP.app.repository.FreightRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
