package com.SpringBootDI.demo.Service;

import com.SpringBootDI.demo.Entity.Vehicle;
import com.SpringBootDI.demo.Repository.FleetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FleetServiceImpl implements FleetService {

    @Autowired
    private FleetRepository fleetRepository;


    @Override
    public void addVehicle(String name, String model, int year, double mileage) {
        fleetRepository.addVehicle(name, model, year, mileage);
    }

    @Override
    public void removeVehicle(int vehicleId) {
        fleetRepository.removeVehicle(vehicleId);
    }

    @Override
    public void updateVehicle(int vehicleId, String name, String model, int year, double mileage) {
        fleetRepository.updateVehicle(vehicleId, name, model, year, mileage);
    }

    @Override
    public Vehicle getVehicle(int vehicleId) {
        return fleetRepository.getVehicle(vehicleId);
    }
}
