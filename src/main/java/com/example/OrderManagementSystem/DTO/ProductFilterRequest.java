package com.example.OrderManagementSystem.DTO;

import lombok.Data;

@Data
public class ProductFilterRequest {
    private String name;
    private String sortBy = "id";
    private String direction = "asc";
    private int page;
    private int size=5;
    private Double minPrice;
    private Double maxPrice;
}
