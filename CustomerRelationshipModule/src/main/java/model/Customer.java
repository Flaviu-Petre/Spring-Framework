package model;


import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {

    //region Fields
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int custId;

    @Column(name = "custName", nullable = false)
    private String custName;

    @Column(name = "custContactNo", nullable = false, unique = true)
    private String custContactNo;

    @Column(name = "custEmail", nullable = false, unique = true)
    private String custEmail;

    @Column(name = "custCity", nullable = false)
    private String custCity;
    //endregion

    //region Constructors
    public Customer() {

    }

    public Customer(String custName, String custContactNo, String custEmail, String custCity) {
        this.custName = custName;
        this.custContactNo = custContactNo;
        this.custEmail = custEmail;
        this.custCity = custCity;
    }
    //endregion

    //region Getters

    public int getCustId() {
        return custId;
    }

    public String getCustName() {
        return custName;
    }

    public String getCustContactNo() {
        return custContactNo;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public String getCustCity() {
        return custCity;
    }

    //endregion

    //region Setters

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public void setCustContactNo(String custContactNo) {
        this.custContactNo = custContactNo;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }

    //endregion

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", custName='" + custName + '\'' +
                ", custContactNo='" + custContactNo + '\'' +
                ", custEmail='" + custEmail + '\'' +
                ", custCity='" + custCity + '\'' +
                '}';
    }
}
