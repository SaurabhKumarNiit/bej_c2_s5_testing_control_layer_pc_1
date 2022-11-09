package com.practice.CustomerData.controller;
import com.practice.CustomerData.customException.ProductAlreadyExistsException;
import com.practice.CustomerData.customException.ProductNotFoundException;
import com.practice.CustomerData.domain.Customer;
import com.practice.CustomerData.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customerData/api/")
public class CustomerController {

    private CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService=customerService;
    }

    @PostMapping("/customer/")
    public ResponseEntity<?>insertCustomer(@RequestBody Customer customer) throws ProductAlreadyExistsException
    {

        ResponseEntity responseEntity = null;
        try {
            customerService.saveCustomer(customer);
            responseEntity = new ResponseEntity(customer, HttpStatus.CREATED);
        }catch (ProductAlreadyExistsException e){

            throw new ProductAlreadyExistsException();

        }
        return responseEntity;
    }

    @GetMapping("/customers/")
    public ResponseEntity<?> fetchAllCustomer()
    {
        ResponseEntity responseEntity = null;
        try {
            responseEntity= new ResponseEntity<>(customerService.getAllCustomerData(),HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
        }
        return responseEntity;
    }

    @DeleteMapping("customer/{customerId}")
    public ResponseEntity<?> deleteSingleCustomer(@PathVariable("customerId") int customerId) throws ProductNotFoundException {

        // return customerService.deleteCustomer(customerId);
        ResponseEntity responseEntity=null;
        try{
            customerService.deleteCustomer(customerId);
            responseEntity=new ResponseEntity<>("Successfully Deleted 1 record",HttpStatus.OK);

        }catch (ProductNotFoundException e){

            throw new ProductNotFoundException();

        }catch (Exception e){
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("customer/{productName}")
    public ResponseEntity<?> fetchByProductName(@PathVariable String productName) throws ProductNotFoundException {

        ResponseEntity responseEntity=null;

        try{
            responseEntity=new ResponseEntity(customerService.getAllCustomerByProductName(productName),HttpStatus.FOUND);
        }catch (Exception e){
           // responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
            throw new ProductNotFoundException();
        }
        return responseEntity;
    }
}
