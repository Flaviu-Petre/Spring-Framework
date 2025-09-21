package com.glo.service.Interface;

import com.glo.client.Fleet;

import java.util.List;

public interface FleetService {
    List<Fleet> getAllFleet();
    Fleet getFleetById(int id);
    void saveFleet(Fleet fleet);
    void updateFleet(Fleet fleet);
    boolean deleteFleet(int id);
}
