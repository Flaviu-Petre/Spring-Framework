package com.glo.repository.Interface;

import com.glo.client.Freight;

import java.util.List;

public interface FreightRepository {
    void createFreight(Freight freight);
    Freight getFreightById(int orderId);
    List<Freight> getAllFreight();
    void updateFreight(Freight freight);
    void deleteFreight(Freight freight);
}
