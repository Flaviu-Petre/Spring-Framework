package com.SpringBootLogging.app.Entity;

public class Fleet {
    //region Fields
    private Long fleetId;
    private String origin;
    private String destination;
    private String noOfFleets;
    private double weight;
    //endregion

    //region Constructors
    public Fleet() {}

    public Fleet(Long fleetId, String origin, String destination, String noOfFleets, double weight) {
        this.fleetId = fleetId;
        this.origin = origin;
        this.destination = destination;
        this.noOfFleets = noOfFleets;
        this.weight = weight;
    }
    //endregion

    //region Getters

    public Long getFleetId() {
        return fleetId;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getNoOfFleets() {
        return noOfFleets;
    }

    public double getWeight() {
        return weight;
    }

    //endregion

    //region Setters

    public void setFleetId(Long fleetId) {
        this.fleetId = fleetId;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setNoOfFleets(String noOfFleets) {
        this.noOfFleets = noOfFleets;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    //endregion

    @Override
    public String toString() {
        return "Fleet [fleetId=" + fleetId + ", origin=" + origin + ", destination=" + destination + ", noOfFleets="
                + noOfFleets + ", weight=" + weight + "]";
    }

}
