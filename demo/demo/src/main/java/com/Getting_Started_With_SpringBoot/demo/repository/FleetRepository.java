package com.Getting_Started_With_SpringBoot.demo.repository;

import com.Getting_Started_With_SpringBoot.demo.Entity.Fleet;

import java.util.List;

public interface FleetRepository {
    List<Fleet> getAllFleets();
    Fleet getFleetById(Long fleetId);
    void createFleet(Fleet fleet);
    void updateFleet(Long fleetId, Fleet fleetDetails);
    void deleteFleet(Long fleetId);
}
