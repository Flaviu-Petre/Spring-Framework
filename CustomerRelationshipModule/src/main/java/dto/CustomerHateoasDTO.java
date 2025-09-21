package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.RepresentationModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerHateoasDTO extends RepresentationModel<CustomerHateoasDTO> {

    private Integer custId;
    private String custName;
    private String custContactNo;
    private String custEmail;
    private String custCity;

    // Default constructor
    public CustomerHateoasDTO() {}

    // Constructor with all fields
    public CustomerHateoasDTO(Integer custId, String custName, String custContactNo,
                              String custEmail, String custCity) {
        this.custId = custId;
        this.custName = custName;
        this.custContactNo = custContactNo;
        this.custEmail = custEmail;
        this.custCity = custCity;
    }

    // Constructor from CustomerDTO
    public CustomerHateoasDTO(CustomerDTO customerDTO) {
        this.custId = customerDTO.getCustId();
        this.custName = customerDTO.getCustName();
        this.custContactNo = customerDTO.getCustContactNo();
        this.custEmail = customerDTO.getCustEmail();
        this.custCity = customerDTO.getCustCity();
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