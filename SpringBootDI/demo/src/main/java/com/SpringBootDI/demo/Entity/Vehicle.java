package com.SpringBootDI.demo.Entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Vehicle {
    //region Fields
    private int vehicleId;
    private String name;
    private String model;
    private int year;
    private double mileage;
    //endregion

    //region Constructors
    public Vehicle(int vehicleId, String name, String model, int year, double mileage) {
        this.vehicleId = vehicleId;
        this.name = name;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
    }

    public Vehicle() {
    }
    //endregion

    //region Getters

    public int getVehicleId() {
        return vehicleId;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getMileage() {
        return mileage;
    }

    //endregion

    //region Setters

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    //endregion

    @Override
    public String toString() {
        return "Vehicle [vehicleId=" + vehicleId + ", name=" + name + ", model=" + model + ", year=" + year
                + ", mileage=" + mileage + "]";
    }
}
