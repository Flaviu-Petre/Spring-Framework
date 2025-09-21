package com.AOP.app.client;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Fleet{
    //region Fields
    private int fleetId;
    private int requiredFleets;
    private String origin;
    private String destination;
    //endregion

    //region Constructors
    public Fleet() {
    }

    public Fleet(int fleetId, int requiredFleets, String origin, String destination) {
        this.fleetId = fleetId;
        this.requiredFleets = requiredFleets;
        this.origin = origin;
        this.destination = destination;
    }
    //endregion

    //region Getters

    public int getFleetId() {
        return fleetId;
    }

    public int getRequiredFleets() {
        return requiredFleets;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    //endregion

    //region Setters

    public void setFleetId(int fleetId) {
        this.fleetId = fleetId;
    }

    public void setRequiredFleets(int requiredFleets) {
        this.requiredFleets = requiredFleets;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    //endregion


    //endregion


}
