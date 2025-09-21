package com.glo.repository;

import com.glo.client.Freight;
import com.glo.repository.Interface.FreightRepository;

import java.util.ArrayList;
import java.util.List;

public class FastFreightRepositoryImpl implements FreightRepository {
    private List<Freight> freightList;

    public FastFreightRepositoryImpl() {
        this.freightList = new ArrayList<>();
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
        System.out.println("FastFreightRepository: Freight created with express processing - " + freight.getOrderId());
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
        return new ArrayList<>(freightList);
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
            System.out.println("FastFreightRepository: Freight updated with priority handling - " + freight.getOrderId());
        }
    }

    @Override
    public void deleteFreight(Freight freight) {
        freightList.removeIf(freights -> freights.getOrderId() == freight.getOrderId());
        System.out.println("FastFreightRepository: Freight deleted with fast processing - " + freight.getOrderId());
    }
}
