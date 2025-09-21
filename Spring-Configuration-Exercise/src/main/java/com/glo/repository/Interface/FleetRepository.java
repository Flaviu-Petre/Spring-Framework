package com.glo.repository.Interface;

import com.glo.client.Fleet;

import java.util.List;

public interface FleetRepository {
    List<Fleet> getAllFleet();
    Fleet getFleetById(int id);
    void saveFleet(Fleet fleet);
    void updateFleet(Fleet fleet);
    boolean deleteFleet(int id);
}
