package com.practice.CustomerData.repository;

import com.practice.CustomerData.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer,Integer> {
    @Query("{'customerProduct.productName':{$in : [?0]}}")
    List<Customer> findAllCustomerFromProductName(String productName);
}
