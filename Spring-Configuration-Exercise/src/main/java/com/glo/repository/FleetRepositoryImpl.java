package com.glo.repository;

import com.glo.client.Fleet;
import com.glo.repository.Interface.FleetRepository;

import java.util.List;

public class FleetRepositoryImpl implements FleetRepository {
    private List<Fleet> fleetList;

    public FleetRepositoryImpl(List<Fleet> fleetList) {
        this.fleetList = fleetList;
    }


    @Override
    public List<Fleet> getAllFleet() {
        return fleetList;
    }

    @Override
    public Fleet getFleetById(int id) {
        for (Fleet fleet : fleetList) {
            if (fleet.getFleetId() == id) {
                return fleet;
            }
        }
        return null;
    }

    @Override
    public void saveFleet(Fleet fleet) {
        fleetList.add(fleet);
    }

    @Override
    public void updateFleet(Fleet fleet) {
        int index = -1;
        for (int i = 0; i < fleetList.size(); i++) {
            if (fleetList.get(i).getFleetId() == fleet.getFleetId()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            fleetList.set(index, fleet);
        }
    }

    @Override
    public boolean deleteFleet(int id) {
        return fleetList.removeIf(fleet -> fleet.getFleetId() == id);
    }
}
