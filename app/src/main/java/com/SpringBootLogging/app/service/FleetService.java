package com.SpringBootLogging.app.service;

import com.SpringBootLogging.app.Entity.Fleet;

import java.util.List;

public interface FleetService {
    List<Fleet> getAllFleets();
    Fleet getFleetById(Long fleetId);
    void createFleet(Fleet fleet);
    void updateFleet(Long fleetId, Fleet fleetDetails);
    void deleteFleet(Long fleetId);
}
