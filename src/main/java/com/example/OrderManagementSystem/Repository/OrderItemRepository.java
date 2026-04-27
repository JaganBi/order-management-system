package com.example.OrderManagementSystem.Repository;

import com.example.OrderManagementSystem.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
