package com.mindhub.product_microservice.dtos;

public class ProductDtoOutput {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private Integer stock;

    public ProductDtoOutput() {
    }

    public ProductDtoOutput(Long id, String name, String description, Double price, Integer stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() {
        return id;
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
