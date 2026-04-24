package com.example.OrderManagementSystem.Specification;

import com.example.OrderManagementSystem.DTO.ProductFilterRequest;
import com.example.OrderManagementSystem.Entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> hasName(String name) {
        return (root, query, cb)
                -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> priceGreaterThan(double min) {
        return (root, query, cb)
                -> cb.greaterThan(root.get("price"), min);
    }

    public static Specification<Product> priceLessThan(double max) {
        return (root, query, cb)
                -> cb.lessThan(root.get("price"), max);
    }

    public static Specification<Product> priceBetween(Double min, Double max) {
        return (root, query, cb) ->
                cb.between(root.get("price"), min, max);
    }
}
