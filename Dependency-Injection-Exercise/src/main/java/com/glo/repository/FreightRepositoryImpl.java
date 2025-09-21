package com.glo.repository;

import com.glo.client.Freight;

import java.util.List;

public class FreightRepositoryImpl implements FreightRepository {
    private List<Freight> freightList;

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
