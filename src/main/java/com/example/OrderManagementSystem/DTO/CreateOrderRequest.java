package com.example.OrderManagementSystem.DTO;

import lombok.Data;

import java.util.List;
@Data
public class CreateOrderRequest {
    private Long userId;
    private List<OrderItemRequest> items;
}
