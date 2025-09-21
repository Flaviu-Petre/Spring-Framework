package com.SpringBootDI.demo.Repository;

import com.SpringBootDI.demo.Entity.Vehicle;

public interface FleetRepository {
    void addVehicle(String name, String model, int year, double mileage);
    void removeVehicle(int vehicleId);
    void updateVehicle(int vehicleId, String name, String model, int year, double mileage);
    Vehicle getVehicle(int vehicleId);
}
