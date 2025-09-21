package com.glo.client;

public class Freight {
    //region Fields
    private int orderId;
    private String origin;
    private String destination;
    //endregion

    //region Constructors
    public Freight() {}

    public Freight(int orderId, String origin, String destination) {
        this.orderId = orderId;
        this.origin = origin;
        this.destination = destination;
    }
    //endregion

    //region Getters

    public int getOrderId() {
        return orderId;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    //endregion

    //region Setters

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    //endregion

    @Override
    public String toString() {
        return "Freight{" +
                "orderId=" + orderId +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}
