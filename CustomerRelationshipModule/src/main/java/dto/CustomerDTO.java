package dto;

import jakarta.validation.constraints.*;

public class CustomerDTO {

    private Integer custId;

    @NotBlank(message = "{customer.name.required}")
    @Size(min = 2, max = 50, message = "{customer.name.size}")
    private String custName;

    @NotBlank(message = "{customer.contactno.required}")
    @Pattern(regexp = "^[0-9]{10}$", message = "{customer.contactno.pattern}")
    private String custContactNo;

    @NotBlank(message = "{customer.email.required}")
    @Email(message = "{customer.email.invalid}")
    @Size(max = 100, message = "{customer.email.size}")
    private String custEmail;

    @NotBlank(message = "{customer.city.required}")
    @Size(min = 2, max = 50, message = "{customer.city.size}")
    private String custCity;

    // Default constructor
    public CustomerDTO() {}

    // Constructor with all fields
    public CustomerDTO(Integer custId, String custName, String custContactNo, String custEmail, String custCity) {
        this.custId = custId;
        this.custName = custName;
        this.custContactNo = custContactNo;
        this.custEmail = custEmail;
        this.custCity = custCity;
    }

    // Getters and Setters
    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustContactNo() {
        return custContactNo;
    }

    public void setCustContactNo(String custContactNo) {
        this.custContactNo = custContactNo;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCustCity() {
        return custCity;
    }

    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }
}