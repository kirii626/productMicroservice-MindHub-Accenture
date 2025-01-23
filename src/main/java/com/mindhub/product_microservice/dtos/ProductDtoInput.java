package com.mindhub.product_microservice.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductDtoInput {

    @NotBlank(message = "The name can´t be null")
    private String name;

    @NotBlank(message = "The description can´t be null")
    private String description;

    @NotNull(message = "The price can´t be null")
    @Min(0)
    private Double price;

    @NotNull(message = "The stock can´t be null")
    @DecimalMin(value = "0.0")
    private Integer stock;

    public ProductDtoInput() {
    }

    public ProductDtoInput(String name, String description, Double price, Integer stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }
}
