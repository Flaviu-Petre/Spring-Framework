package com.SpringDataJPA.PaginationAndSorting.repository;

import com.SpringDataJPA.PaginationAndSorting.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer>, JpaRepository<Customer, Integer> {
    boolean existsByCustEmail(String custEmail);

    List<Customer> findByCustEmailEndingWith(String emailSuffix);

    Page<Customer> findByCustEmailEndingWith(String emailSuffix, Pageable pageable);

    Optional<Customer> findByCustEmail(String custEmail);

    Optional<Customer> findByCustId(Integer custId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Customer c WHERE c.custEmail = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT c FROM Customer c WHERE c.custEmail = :email")
    Optional<Customer> findCustomerByEmail(@Param("email") String email);



}
