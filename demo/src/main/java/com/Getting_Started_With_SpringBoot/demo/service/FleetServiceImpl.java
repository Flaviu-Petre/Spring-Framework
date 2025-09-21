package com.Getting_Started_With_SpringBoot.demo.service;

import com.Getting_Started_With_SpringBoot.demo.Entity.Fleet;
import com.Getting_Started_With_SpringBoot.demo.repository.FleetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FleetServiceImpl implements FleetService {

    @Autowired
    private FleetRepository fleetRepository;

    public FleetServiceImpl(FleetRepository fleetRepository) {
        this.fleetRepository = fleetRepository;
    }

    @Override
    public List<Fleet> getAllFleets() {
        return fleetRepository.getAllFleets();
    }

    @Override
    public Fleet getFleetById(Long fleetId) {
        return fleetRepository.getFleetById(fleetId);
    }

    @Override
    public void createFleet(Fleet fleet) {
        fleetRepository.createFleet(fleet);
    }

    @Override
    public void updateFleet(Long fleetId, Fleet fleetDetails) {
        fleetRepository.updateFleet(fleetId, fleetDetails);
    }

    @Override
    public void deleteFleet(Long fleetId) {
        fleetRepository.deleteFleet(fleetId);
    }
}
