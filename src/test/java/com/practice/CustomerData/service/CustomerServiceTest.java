package com.practice.CustomerData.service;

import com.practice.CustomerData.customException.ProductAlreadyExistsException;
import com.practice.CustomerData.customException.ProductNotFoundException;
import com.practice.CustomerData.domain.Customer;
import com.practice.CustomerData.domain.Product;
import com.practice.CustomerData.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer1,customer2;
    List<Customer> customerList;

    Product product1,product2;

    @BeforeEach
    public void setUp()
    {
        product1=new Product(65,"Samsung SmartPhone","Made in India");
        customer1=new Customer(110,"Suresh",9788154656L,product1);
        product2=new Product(70,"Samsung SmartTV","Made in India");
        customer2=new Customer(102,"Ramesh",7788154656L,product2);
        customerList = Arrays.asList(customer1, customer2);
    }
    @AfterEach
    void tearDown(){
        customer1=null;
        customer2 = null;
    }

    @Test
    public void givenCustomerToSaveData() throws ProductAlreadyExistsException
    {
        when(customerRepository.findById(customer1.getCustomerId())).thenReturn(Optional.ofNullable(null));
        when(customerRepository.save(any())).thenReturn(customer1);
        assertEquals(customer1,customerService.saveCustomer(customer1));
        verify(customerRepository,times(1)).save(any());
        verify(customerRepository,times(1)).findById(any());
    }
    @Test
    public void givenCustomerToSaveReturnCustomerFailure(){
        when(customerRepository.findById(customer1.getCustomerId())).thenReturn(Optional.ofNullable(customer1));
        assertThrows(ProductAlreadyExistsException.class,()->customerService.saveCustomer(customer1));
        verify(customerRepository,times(0)).save(any());
        verify(customerRepository,times(1)).findById(any());
    }

    @Test
    public void givenCustomerToDeleteShouldDeleteSuccess() throws ProductNotFoundException {
        when(customerRepository.findById(customer1.getCustomerId())).thenReturn(Optional.ofNullable(customer1));
        boolean flag = customerService.deleteCustomer(customer1.getCustomerId());
        assertEquals(true,flag);

        verify(customerRepository,times(1)).deleteById(any());
        verify(customerRepository,times(1)).findById(any());
    }

}
