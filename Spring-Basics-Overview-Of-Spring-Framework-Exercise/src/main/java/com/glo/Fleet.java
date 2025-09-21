package com.glo;

public class Fleet {
    //region Fields
    private long fleetId;
    private String origin;
    private String destination;
    private int requiredFleets;
    //endregion

    //region Constroctor

    public Fleet(long fleetId, String origin, String destination, int requiredFleets) {
        this.fleetId = fleetId;
        this.origin = origin;
        this.destination = destination;
        this.requiredFleets = requiredFleets;
    }
    //endregion

    //region Getters

    public long getFleetId() {
        return fleetId;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public int getRequiredFleets() {
        return requiredFleets;
    }

    //endregion

    //region Setters

    public void setFleetId(long fleetId) {
        this.fleetId = fleetId;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setRequiredFleets(int requiredFleets) {
        this.requiredFleets = requiredFleets;
    }

    //endregion
}
