package com.mindhub.product_microservice.exceptions;

public class ProductAlreadyExistsExc extends RuntimeException {
    public ProductAlreadyExistsExc(String message) {
        super(message);
    }
}
