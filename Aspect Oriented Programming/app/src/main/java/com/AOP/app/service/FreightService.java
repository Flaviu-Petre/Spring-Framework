package com.AOP.app.service;

import com.AOP.app.client.Freight;

import java.util.List;

public interface FreightService {
    void createFreight(Freight freight);
    Freight getFreightById(int orderId);
    List<Freight> getAllFreight();
    void updateFreight(Freight freight);
    void deleteFreight(int orderId);
}
