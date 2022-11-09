package com.practice.CustomerData.service;

import com.practice.CustomerData.customException.ProductAlreadyExistsException;
import com.practice.CustomerData.customException.ProductNotFoundException;
import com.practice.CustomerData.domain.Customer;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer customer) throws ProductAlreadyExistsException;

    List<Customer> getAllCustomerData() throws Exception;

    boolean deleteCustomer(int customerId) throws ProductNotFoundException;

    List<Customer>getAllCustomerByProductName(String productName) throws ProductNotFoundException;
}
