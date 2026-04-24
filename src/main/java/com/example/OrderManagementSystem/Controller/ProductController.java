package com.example.OrderManagementSystem.Controller;

import com.example.OrderManagementSystem.DTO.ProductFilterRequest;
import com.example.OrderManagementSystem.DTO.ProductRequest;
import com.example.OrderManagementSystem.DTO.ProductResponse;
import com.example.OrderManagementSystem.Service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping
    public ProductResponse addProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }
    @GetMapping("/all")
    public List<ProductResponse> getAll() {
        return productService.getAll();
    }
   @GetMapping
    public List<ProductResponse> getFilterProducts(@ModelAttribute ProductFilterRequest productFilterRequest){
    return productService.getAllProductsByCategory(productFilterRequest);
    }
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable long id) {
        return productService.findProductById(id);
    }
    @PatchMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable long id, @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }
    @DeleteMapping("/{id}")
    public String deleteProductById(@PathVariable long id) {
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }
}
