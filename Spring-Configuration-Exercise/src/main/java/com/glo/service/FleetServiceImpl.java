package com.glo.service;

import com.glo.client.Fleet;
import com.glo.repository.Interface.FleetRepository;
import com.glo.service.Interface.FleetService;

import java.util.List;

public class FleetServiceImpl implements FleetService {
    private FleetRepository fleetRepository;

    public FleetServiceImpl(FleetRepository fleetRepository) {
        this.fleetRepository = fleetRepository;
    }

    @Override
    public List<Fleet> getAllFleet() {
        return fleetRepository.getAllFleet();
    }

    @Override
    public Fleet getFleetById(int id) {
        return fleetRepository.getFleetById(id);
    }

    @Override
    public void saveFleet(Fleet fleet) {
        fleetRepository.saveFleet(fleet);
    }

    @Override
    public void updateFleet(Fleet fleet) {
        fleetRepository.updateFleet(fleet);
    }

    @Override
    public boolean deleteFleet(int id) {
        return fleetRepository.deleteFleet(id);
    }
}
