package com.example.OrderManagementSystem.Service;

import com.example.OrderManagementSystem.DTO.ProductRequest;
import com.example.OrderManagementSystem.DTO.ProductResponse;
import com.example.OrderManagementSystem.Entity.Product;
import com.example.OrderManagementSystem.Exception.ProductNotFoundException;
import com.example.OrderManagementSystem.Mapper.ProductMapper;
import com.example.OrderManagementSystem.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = ProductMapper.toEntity(productRequest);
        Product saved = productRepository.save(product);
        return ProductMapper.toResponse(saved);
    }
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(ProductMapper::toResponse).toList();
    }
    public ProductResponse findProductById(Long id) {
        return ProductMapper.toResponse(productRepository.findById(Math.toIntExact(id))
                .orElseThrow(()-> new ProductNotFoundException("Product not found")));
    }
    public ProductResponse updateProduct(Long id,ProductRequest productRequest) {
        Product product = productRepository.findById(Math.toIntExact(id)).orElseThrow(()->new ProductNotFoundException("Product not found"));
        if(productRequest.getName()!=null){
            product.setName(productRequest.getName());
        }
        if(productRequest.getPrice()!=null){
            product.setPrice(productRequest.getPrice());
        }
        if(productRequest.getQuantity()!=null){
            product.setQuantity(productRequest.getQuantity());
        }
        return ProductMapper.toResponse(productRepository.save(product));

    }
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(Math.toIntExact(id)).orElseThrow(()->new ProductNotFoundException("Product not found"));
        productRepository.delete(product);
    }


}
