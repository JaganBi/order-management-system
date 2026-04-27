package com.example.OrderManagementSystem.Service;

import com.example.OrderManagementSystem.DTO.CreateOrderRequest;
import com.example.OrderManagementSystem.DTO.OrderItemRequest;
import com.example.OrderManagementSystem.Entity.Order;
import com.example.OrderManagementSystem.Entity.OrderItem;
import com.example.OrderManagementSystem.Entity.Product;
import com.example.OrderManagementSystem.Entity.User;
import com.example.OrderManagementSystem.Repository.OrderRepository;
import com.example.OrderManagementSystem.Repository.ProductRepository;
import com.example.OrderManagementSystem.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    public OrderService(ProductRepository productRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }
    @Transactional
    public Order createOrder(CreateOrderRequest request){
        User user = userRepository.findById(request
                .getUserId()).orElseThrow(() -> new RuntimeException("No User found"));
        if(request.getItems()==null || request.getItems().isEmpty()){
            throw  new RuntimeException("Order must contain at least one item");
        }
        Order order = new Order();
        order.setUser(user);
        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0.0;
        for(OrderItemRequest orderItemRequest:request.getItems()){
            int qty =  orderItemRequest.getQuantity();
            if(qty<=0){
                throw  new RuntimeException("Invalid quantity");
            }
            Product product = productRepository.findByIdForUpdate(orderItemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("No Product found"));
            if(product.getQuantity()<qty){
                throw  new RuntimeException("Insufficient stock for product: " + product.getName());
            }
            //deduct quantity
            product.setQuantity(product.getQuantity()-qty);

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(orderItemRequest.getQuantity());
            item.setPrice(product.getPrice());
            item.setOrder(order);
            total = total + orderItemRequest.getQuantity()*product.getPrice();
            orderItems.add(item);
        }
        order.setOrderItems(orderItems);
        order.setUser(user);
        order.setTotalAmount(total);
        return orderRepository.save(order);

    }
}
