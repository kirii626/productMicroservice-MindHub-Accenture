package com.mindhub.product_microservice.exceptions;

public class InvalidCredentialsExc extends RuntimeException {
    public InvalidCredentialsExc(String message) {
        super(message);
    }
}
