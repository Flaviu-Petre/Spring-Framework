package com.SpringBootDI.demo.Service;

import com.SpringBootDI.demo.Entity.Vehicle;

public interface FleetService {
    void addVehicle(String name, String model, int year, double mileage);
    void removeVehicle(int vehicleId);
    void updateVehicle(int vehicleId, String name, String model, int year, double mileage);
    Vehicle getVehicle(int vehicleId);
}
