package com.example.OrderManagementSystem.Mapper;

import com.example.OrderManagementSystem.DTO.ProductRequest;
import com.example.OrderManagementSystem.DTO.ProductResponse;
import com.example.OrderManagementSystem.Entity.Product;

public class ProductMapper {
    public static Product toEntity(ProductRequest request){
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        return product;
    }
    public static ProductResponse toResponse(Product product){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setQuantity(product.getQuantity());
        return productResponse;
    }
}
