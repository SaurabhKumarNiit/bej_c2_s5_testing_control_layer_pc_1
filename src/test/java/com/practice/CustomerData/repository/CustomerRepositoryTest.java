package com.practice.CustomerData.repository;

import com.practice.CustomerData.domain.Customer;
import com.practice.CustomerData.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;
    private Product product;

    @BeforeEach
    public void setUp(){

        product=new Product(51,"Samsung Smartphone","Made in South Korean");
        customer=new Customer(101,"Ramesh",9752154656L,product);

    }

    @Test
    @DisplayName("Test case for saving customer object")
    void givenCustomerToSaveShouldReturnSavedCustomer() {
        customerRepository.save(customer);
        Customer customer1 = customerRepository.findById(customer.getCustomerId()).get();
        assertNotNull(customer1);
        assertEquals(customer.getCustomerId(), customer1.getCustomerId());
    }


    @Test
    @DisplayName("Test case for deleting customer object")
    public void givenCustomerToDeleteShouldDeleteCustomer() {
        customerRepository.insert(customer);
        Customer customer1 = customerRepository.findById(customer.getCustomerId()).get();
        customerRepository.delete(customer1);
        assertEquals(Optional.empty(), customerRepository.findById(customer.getCustomerId()));

    }

    @Test
    @DisplayName("Test case for retrieving all the  customer object")
    public void givenCustomerReturnAllCustomerDetails() {

         customerRepository.insert(customer);
        Product product1=new Product(55,"Samsung SmartTV","Made in India");
        Customer customer1=new Customer(102,"Suresh",9788154656L,product);
        customerRepository.insert(customer1);

        List<Customer> list = customerRepository.findAll();
        assertEquals(2, list.size());
        assertEquals("Suresh", list.get(1).getCustomerName());

    }


    @AfterEach
    void tearDown() {
        product = null;
        customer = null;
        customerRepository.deleteAll();

    }

}
