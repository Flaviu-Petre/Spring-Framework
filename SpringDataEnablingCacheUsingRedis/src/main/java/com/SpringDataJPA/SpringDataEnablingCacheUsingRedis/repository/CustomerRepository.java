package com.SpringDataJPA.SpringDataEnablingCacheUsingRedis.repository;

import com.SpringDataJPA.SpringDataEnablingCacheUsingRedis.model.Customer;
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
public interface CustomerRepository extends PagingAndSortingRepository<com.SpringDataJPA.SpringDataEnablingCacheUsingRedis.model.Customer, Integer>, JpaRepository<com.SpringDataJPA.SpringDataEnablingCacheUsingRedis.model.Customer, Integer> {
    boolean existsByCustEmail(String custEmail);

    List<com.SpringDataJPA.SpringDataEnablingCacheUsingRedis.model.Customer> findByCustEmailEndingWith(String emailSuffix);

    Page<com.SpringDataJPA.SpringDataEnablingCacheUsingRedis.model.Customer> findByCustEmailEndingWith(String emailSuffix, Pageable pageable);

    Optional<com.SpringDataJPA.SpringDataEnablingCacheUsingRedis.model.Customer> findByCustEmail(String custEmail);

    Optional<com.SpringDataJPA.SpringDataEnablingCacheUsingRedis.model.Customer> findByCustId(Integer custId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Customer c WHERE c.custEmail = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT c FROM Customer c WHERE c.custEmail = :email")
    Optional<com.SpringDataJPA.SpringDataEnablingCacheUsingRedis.model.Customer> findCustomerByEmail(@Param("email") String email);



}
