package com.spring_rest.customer_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {

    //region Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer custId;

    private String custName;
    private String ContactNo;
    private String custEmail;
    private String custCity;
    private String Address;
    //endregion

    //region Constructors
    public Customer() {

    }

    public Customer(String custName, String contactNo, String custEmail, String custCity, String address) {
        this.custName = custName;
        ContactNo = contactNo;
        this.custEmail = custEmail;
        this.custCity = custCity;
        Address = address;
    }
    //endregion

    //region Getters

    public Integer getCustId() {
        return custId;
    }

    public String getCustName() {
        return custName;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public String getCustCity() {
        return custCity;
    }

    public String getAddress() {
        return Address;
    }

    //endregion

    //region Setters

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }

    public void setAddress(String address) {
        Address = address;
    }

    //endregion
}

//generate Json file to add a customer

// {
//     "custName": "John Doe",
//     "contactNo": "123-456-7890",
//     "custEmail": "
//     "custCity": "New York",
//     "address": "123 Main St, New York, NY 10001"
// }