package com.practice.CustomerData.service;

import com.practice.CustomerData.customException.ProductAlreadyExistsException;
import com.practice.CustomerData.customException.ProductNotFoundException;
import com.practice.CustomerData.domain.Customer;
import com.practice.CustomerData.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepository customerRepository;

    public CustomerServiceImpl (CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) throws ProductAlreadyExistsException {
        if(customerRepository.findById(customer.getCustomerId()).isPresent()){
            throw new ProductAlreadyExistsException();
        }
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomerData() throws Exception {
        return customerRepository.findAll();
    }

    @Override
    public boolean deleteCustomer(int customerId) throws ProductNotFoundException {
        boolean result=false;

        if(customerRepository.findById(customerId).isEmpty()){
            throw new ProductNotFoundException();
        }
        else {
            customerRepository.deleteById(customerId);
            result= true;
        }
        return result;
    }

    @Override
    public List<Customer> getAllCustomerByProductName(String productName) throws ProductNotFoundException {
        if(customerRepository.findAllCustomerFromProductName(productName).isEmpty()){
            throw new ProductNotFoundException();
        }
        return customerRepository.findAllCustomerFromProductName(productName);
    }
}
