package com.practice.CustomerData.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.CustomerData.controller.CustomerController;
import com.practice.CustomerData.customException.ProductAlreadyExistsException;
import com.practice.CustomerData.domain.Customer;
import com.practice.CustomerData.domain.Product;
import com.practice.CustomerData.service.CustomerServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CustomerServiceImpl customerService;

    @InjectMocks
    private CustomerController customerController;
    private Customer customer1, customer2;
    private Product product1, product2;
    List<Customer> customerList;

    @BeforeEach
    void setUp() {
        product1=new Product(65,"Samsung SmartPhone","Made in India");
        customer1=new Customer(110,"Suresh",9788154656L,product1);
        product2=new Product(70,"Samsung SmartTV","Made in India");
        customer2=new Customer(102,"Ramesh",7788154656L,product2);
        customerList = Arrays.asList(customer1, customer2);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @AfterEach
    void tearDown() {
        customer1 = null;
        customer2 = null;
    }

    @Test
    public void givenCustomerToSaveReturnSavedCustomer() throws Exception {
        when(customerService.saveCustomer(any())).thenReturn(customer1);
        mockMvc.perform(post("/customerData/api/customer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(customer1)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(customerService, times(1)).saveCustomer(any());

    }

    @Test
    public void givenCustomerToSaveReturnSavedCustomerFailure() throws Exception {
        when(customerService.saveCustomer(any())).thenThrow(ProductAlreadyExistsException.class);
        mockMvc.perform(post("/customerData/api/customer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(customer1)))
                .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(customerService,times(1)).saveCustomer(any());

    }
    @Test
    public void givenCustomerIdDeleteCustomer() throws Exception {
        when(customerService.deleteCustomer(anyInt())).thenReturn(true);
        mockMvc.perform(delete("/customerData/api/customer/101")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(customerService,times(1)).deleteCustomer(anyInt());

    }


    private static String jsonToString(final Object obj) throws JsonProcessingException {
        String result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(obj);
            result = jsonContent;
        } catch(JsonProcessingException e) {
            result = "JSON processing error";
        }

        return result;
    }

}