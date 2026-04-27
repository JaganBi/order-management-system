package com.example.OrderManagementSystem.Repository;

import com.example.OrderManagementSystem.Entity.Order;
import com.example.OrderManagementSystem.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
