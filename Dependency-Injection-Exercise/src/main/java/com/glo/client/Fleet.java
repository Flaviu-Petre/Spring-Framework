package com.glo.client;

import java.util.List;

public class Fleet {
    //region Fields
    private int fleetId;
    private String fleetName;
    private String location;
    private int vehicleCount;
    private List<String> vehicleTypes;
    //endregion

    //region Constructors
    public Fleet() {}

    public Fleet(int fleetId, String fleetName, String location, int vehicleCount) {
        this.fleetId = fleetId;
        this.fleetName = fleetName;
        this.location = location;
        this.vehicleCount = vehicleCount;
    }
    //endregion

    //region Getters
    public int getFleetId() {
        return fleetId;
    }

    public String getFleetName() {
        return fleetName;
    }

    public String getLocation() {
        return location;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public List<String> getVehicleTypes() {
        return vehicleTypes;
    }
    //endregion

    //region Setters
    public void setFleetId(int fleetId) {
        this.fleetId = fleetId;
    }

    public void setFleetName(String fleetName) {
        this.fleetName = fleetName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public void setVehicleTypes(List<String> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }
    //endregion

    @Override
    public String toString() {
        return "Fleet{" +
                "fleetId=" + fleetId +
                ", fleetName='" + fleetName + '\'' +
                ", location='" + location + '\'' +
                ", vehicleCount=" + vehicleCount +
                ", vehicleTypes=" + vehicleTypes +
                '}';
    }
}
