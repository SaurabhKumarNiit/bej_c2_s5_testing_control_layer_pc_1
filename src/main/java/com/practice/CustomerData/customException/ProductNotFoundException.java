package com.practice.CustomerData.customException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND,reason = "Product not found please check")
public class ProductNotFoundException extends Exception{
}
