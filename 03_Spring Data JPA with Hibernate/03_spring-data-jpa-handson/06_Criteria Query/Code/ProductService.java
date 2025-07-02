package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Product;
import com.cognizant.ormlearn.model.ProductFilterRequest;

import java.util.List;

public interface ProductService {
    List<Product> getFilteredProducts(ProductFilterRequest filterRequest);
}
