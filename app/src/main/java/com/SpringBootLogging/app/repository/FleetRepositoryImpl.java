package com.SpringBootLogging.app.repository;

import com.SpringBootLogging.app.Entity.Fleet;
import com.SpringBootLogging.app.repository.FleetRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FleetRepositoryImpl implements FleetRepository {
    private List<Fleet> fleets;

    public FleetRepositoryImpl() {
        this.fleets = new ArrayList<>();
    }

    @Override
    public List<Fleet> getAllFleets() {
        return fleets;
    }

    @Override
    public Fleet getFleetById(Long fleetId) {
        return fleets.stream()
                .filter(fleet -> fleet.getFleetId().equals(fleetId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void createFleet(Fleet fleet) {
        fleets.add(fleet);
    }

    @Override
    public void updateFleet(Long fleetId, Fleet fleetDetails) {
        Fleet fleet = getFleetById(fleetId);
        if (fleet != null) {
            fleet.setOrigin(fleetDetails.getOrigin());
            fleet.setDestination(fleetDetails.getDestination());
            fleet.setNoOfFleets(fleetDetails.getNoOfFleets());
            fleet.setWeight(fleetDetails.getWeight());
        }
    }

    @Override
    public void deleteFleet(Long fleetId) {
        fleets.removeIf(fleet -> fleet.getFleetId().equals(fleetId));
    }
}
