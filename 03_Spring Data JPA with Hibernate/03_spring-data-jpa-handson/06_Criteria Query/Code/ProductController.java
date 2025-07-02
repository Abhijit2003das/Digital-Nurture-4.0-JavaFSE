package com.cognizant.ormlearn.controller;

import com.cognizant.ormlearn.model.Product;
import com.cognizant.ormlearn.model.ProductFilterRequest;
import com.cognizant.ormlearn.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/filter")
    public List<Product> getFilteredProducts(@RequestBody ProductFilterRequest request) {
        return productService.getFilteredProducts(request);
    }

    // ✅ Add this for browser testing
    @GetMapping("/test")
    public List<Product> testFilter() {
        ProductFilterRequest request = new ProductFilterRequest();
        request.setMinCpuSpeed(2.5);
        request.setMinRamSize(8);
        request.setOperatingSystem("Windows");
        request.setCpu("Intel");
        request.setMinReview(3.5);
        return productService.getFilteredProducts(request);
    }
}
