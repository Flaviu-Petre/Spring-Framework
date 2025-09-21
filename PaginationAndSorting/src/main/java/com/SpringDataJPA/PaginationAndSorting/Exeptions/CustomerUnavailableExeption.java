package com.SpringDataJPA.PaginationAndSorting.Exeptions;

public class CustomerUnavailableExeption extends RuntimeException {
    public CustomerUnavailableExeption(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
