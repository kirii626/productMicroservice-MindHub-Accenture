package com.mindhub.product_microservice.dtos;

public class ProductDtoInput {

    private String name;

    private String description;

    private Double price;

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
