package com.example.OrderManagementSystem.DTO;

import lombok.Data;

@Data
public class ProductRequest {
    private Double price;
    private Integer quantity;
    private String name;

}
