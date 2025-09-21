package com.SpringDataJPA.PaginationAndSorting.Response;

import com.SpringDataJPA.PaginationAndSorting.model.Customer;

import java.util.List;

public class CustomerResponse {
    //region fields
    private List<Customer> customers;
    private int pageNo;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private boolean isLast;
    //endregion

    @Override
    public String toString() {
        return "CustomerResponse{" +
                "customers=" + customers +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                ", isLast=" + isLast +
                '}';
    }

    //region Getters

    public List<Customer> getCustomers() {
        return customers;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public boolean isLast() {
        return isLast;
    }

    //endregion

    //region Setters

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    //endregion
}
