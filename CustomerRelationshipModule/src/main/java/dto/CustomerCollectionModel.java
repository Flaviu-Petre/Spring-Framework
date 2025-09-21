package dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class CustomerCollectionModel extends RepresentationModel<CustomerCollectionModel> {

    private List<CustomerHateoasDTO> customers;
    private int pageNo;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private boolean isLast;

    // Default constructor
    public CustomerCollectionModel() {}

    // Constructor with all fields
    public CustomerCollectionModel(List<CustomerHateoasDTO> customers, int pageNo, int pageSize,
                                   int totalPages, long totalElements, boolean isLast) {
        this.customers = customers;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.isLast = isLast;
    }

    // Getters and Setters
    public List<CustomerHateoasDTO> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerHateoasDTO> customers) {
        this.customers = customers;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }
}