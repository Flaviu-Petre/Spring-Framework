package com.glo.service;

import com.glo.client.Freight;

import java.util.List;

public interface FreightService {
    void createFreight(Freight freight);
    Freight getFreightById(int orderId);
    List<Freight> getAllFreight();
    void updateFreight(Freight freight);
    void deleteFreight(int orderId);
}
