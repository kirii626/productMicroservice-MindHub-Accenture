package com.mindhub.product_microservice.exceptions;

public class ProductNotFoundExc extends RuntimeException {
    public ProductNotFoundExc(String message) {
        super(message);
    }
}
