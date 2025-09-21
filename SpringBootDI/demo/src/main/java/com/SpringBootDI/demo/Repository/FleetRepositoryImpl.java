package com.SpringBootDI.demo.Repository;

import com.SpringBootDI.demo.Entity.Vehicle;
import com.SpringBootDI.demo.Service.FleetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FleetRepositoryImpl implements FleetRepository {
    private List<Vehicle> vehicles;

    public FleetRepositoryImpl() {
        this.vehicles = new ArrayList<>();
    }


    @Override
    public void addVehicle(String name, String model, int year, double mileage) {
        int newId = vehicles.size() + 1; // Simple ID generation
        Vehicle newVehicle = new Vehicle(newId, name, model, year, mileage);
        vehicles.add(newVehicle);
    }

    @Override
    public void removeVehicle(int vehicleId) {
        vehicles.removeIf(v -> v.getVehicleId() == vehicleId);
    }

    @Override
    public void updateVehicle(int vehicleId, String name, String model, int year, double mileage) {
        for (Vehicle v : vehicles) {
            if (v.getVehicleId() == vehicleId) {
                v.setName(name);
                v.setModel(model);
                v.setYear(year);
                v.setMileage(mileage);
                break;
            }
        }
    }

    @Override
    public Vehicle getVehicle(int vehicleId) {
        return vehicles.stream()
                .filter(v -> v.getVehicleId() == vehicleId)
                .findFirst()
                .orElse(null);
    }
}
