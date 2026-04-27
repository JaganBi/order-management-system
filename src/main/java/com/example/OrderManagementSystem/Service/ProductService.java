package com.example.OrderManagementSystem.Service;

import com.example.OrderManagementSystem.DTO.ProductFilterRequest;
import com.example.OrderManagementSystem.DTO.ProductRequest;
import com.example.OrderManagementSystem.DTO.ProductResponse;
import com.example.OrderManagementSystem.Entity.Product;
import com.example.OrderManagementSystem.Exception.ProductNotFoundException;
import com.example.OrderManagementSystem.Mapper.ProductMapper;
import com.example.OrderManagementSystem.Repository.ProductRepository;
import com.example.OrderManagementSystem.Specification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    public List<ProductResponse> getAll(){
        return productRepository.findAll()
                .stream().map(ProductMapper::toResponse).toList();
    }
    public List<ProductResponse> getAllProductsByCategory(ProductFilterRequest request) {

        String sortBy = request.getSortBy() != null ? request.getSortBy() : "id";
        String direction = request.getDirection() != null ? request.getDirection() : "asc";

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        int page = request.getPage();
        int size = request.getSize() <= 0 ? 5 : request.getSize();
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Product> spec = (root, query, cb) -> cb.conjunction();
        if (request.getName() != null && !request.getName().isBlank()) {
            spec = spec.and(ProductSpecification.hasName(request.getName()));
        }
        if (request.getMinPrice() != null && request.getMaxPrice() != null) {
            spec = spec.and(ProductSpecification.priceBetween(
                    request.getMinPrice(),
                    request.getMaxPrice()
            ));
        } else {
            if (request.getMinPrice() != null) {
                spec = spec.and(ProductSpecification.priceGreaterThan(request.getMinPrice()));
            }
            if (request.getMaxPrice() != null) {
                spec = spec.and(ProductSpecification.priceLessThan(request.getMaxPrice()));
            }
        }
        Page<Product> productPage = productRepository.findAll(spec, pageable);
        return productPage.getContent()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }
    public ProductResponse findProductById(Long id) {
        return ProductMapper.toResponse(productRepository.findById((long) Math.toIntExact(id))
                .orElseThrow(()-> new ProductNotFoundException("Product not found")));
    }
    public ProductResponse updateProduct(Long id,ProductRequest productRequest) {
        Product product = productRepository.findById((long) Math.toIntExact(id)).orElseThrow(()->new ProductNotFoundException("Product not found"));
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
        Product product = productRepository.findById((long) Math.toIntExact(id)).orElseThrow(()->new ProductNotFoundException("Product not found"));
        productRepository.delete(product);
    }

}
