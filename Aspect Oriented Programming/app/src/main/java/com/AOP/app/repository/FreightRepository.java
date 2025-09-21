package com.AOP.app.repository;

import com.AOP.app.client.Freight;

import java.util.List;

public interface FreightRepository {
    void createFreight(Freight freight);
    Freight getFreightById(int orderId);
    List<Freight> getAllFreight();
    void updateFreight(Freight freight);
    void deleteFreight(Freight freight);
}
