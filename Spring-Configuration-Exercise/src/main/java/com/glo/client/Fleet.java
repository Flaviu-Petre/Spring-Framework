package com.glo.client;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.util.List;

public class Fleet implements InitializingBean, DisposableBean {
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

    //region Methods
    public static Fleet createFleetInstance(int fleetId, int requiredFleets, String origin, String destination) {
        Fleet fleet = new Fleet(fleetId, requiredFleets, origin, destination);
        System.out.println("Factory method: Fleet instance created with ID: " + fleetId);
        return fleet;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean: afterPropertiesSet called for Fleet ID: " + this.fleetId);
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean: destroy method called for Fleet ID: " + this.fleetId);
    }

    public void customInitMethod() {
        System.out.println("Custom Init Method: customInitMethod called for Fleet ID: " + this.fleetId);
    }

    public void customDestroyMethod() {
        System.out.println("Custom Destroy Method: customDestroyMethod called for Fleet ID: " + this.fleetId);
    }

    @PostConstruct
    public void annotationInitMethod() {
        System.out.println("@PostConstruct: annotationInitMethod called for Fleet ID: " + this.fleetId);
    }

    @PreDestroy
    public void annotationDestroyMethod() {
        System.out.println("@PreDestroy: annotationDestroyMethod called for Fleet ID: " + this.fleetId);
    }

    @Override
    public String toString() {
        return "Fleet{" +
                "fleetId=" + fleetId +
                ", requiredFleets=" + requiredFleets +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
    //endregion


}
