package com.Getting_Started_With_SpringBoot.demo.service;

import com.Getting_Started_With_SpringBoot.demo.Entity.Fleet;

import java.util.List;

public interface FleetService {
    List<Fleet> getAllFleets();
    Fleet getFleetById(Long fleetId);
    void createFleet(Fleet fleet);
    void updateFleet(Long fleetId, Fleet fleetDetails);
    void deleteFleet(Long fleetId);
}
