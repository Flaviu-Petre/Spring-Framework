package com.AOP.app.repository;

import com.AOP.app.client.Freight;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FreightRepositoryImpl implements FreightRepository {
    private List<Freight> freightList;

    @PostConstruct
    public void initializeFreightList() {
        this.freightList = new ArrayList<>();

        freightList.add(new Freight(1001, "New York", "Los Angeles"));
        freightList.add(new Freight(1002, "Chicago", "Miami"));
        freightList.add(new Freight(1003, "Seattle", "Boston"));
    }

    public List<Freight> getFreightList() {
        return freightList;
    }

    public void setFreightList(List<Freight> freightList) {
        this.freightList = freightList;
    }

    @Override
    public void createFreight(Freight freight) {
        freightList.add(freight);
    }

    @Override
    public Freight getFreightById(int orderId) {
        for (Freight freight : freightList) {
            if (freight.getOrderId() == orderId) {
                return freight;
            }
        }
        return null;
    }

    @Override
    public List<Freight> getAllFreight() {
        return freightList;
    }

    @Override
    public void updateFreight(Freight freight) {
        int index = -1;
        for (int i = 0; i < freightList.size(); i++) {
            if (freightList.get(i).getOrderId() == freight.getOrderId()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            freightList.set(index, freight);
        }
    }

    @Override
    public void deleteFreight(Freight freight) {
        freightList.removeIf(freights -> freights.getOrderId() == freight.getOrderId());
    }
}