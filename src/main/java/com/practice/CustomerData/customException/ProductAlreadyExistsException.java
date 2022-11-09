package com.practice.CustomerData.customException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT,reason = "Product Already Exists please check")
public class ProductAlreadyExistsException extends Exception {
}
