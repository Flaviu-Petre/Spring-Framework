package dto;

import java.util.Map;

public class CustomerStatsDTO {

    private int totalCustomers;
    private int totalCities;
    private Map<String, Long> customersByCity;

    // Default constructor
    public CustomerStatsDTO() {}

    // Constructor with all fields
    public CustomerStatsDTO(int totalCustomers, int totalCities, Map<String, Long> customersByCity) {
        this.totalCustomers = totalCustomers;
        this.totalCities = totalCities;
        this.customersByCity = customersByCity;
    }

    // Getters and Setters
    public int getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(int totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public int getTotalCities() {
        return totalCities;
    }

    public void setTotalCities(int totalCities) {
        this.totalCities = totalCities;
    }

    public Map<String, Long> getCustomersByCity() {
        return customersByCity;
    }

    public void setCustomersByCity(Map<String, Long> customersByCity) {
        this.customersByCity = customersByCity;
    }
}