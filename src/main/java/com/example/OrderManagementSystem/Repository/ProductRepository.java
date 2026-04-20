package com.example.OrderManagementSystem.Repository;

import com.example.OrderManagementSystem.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
