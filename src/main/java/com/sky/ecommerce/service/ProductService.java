package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.model.Product;
import com.sky.ecommerce.request.CreateProductRequest;
import org.springframework.data.domain.Page;


import java.util.List;

public interface ProductService {
    public Product createProduct(CreateProductRequest req);

    public String deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Long productId, Product product)  throws  ProductException;

    public  Product findProductById(Long id) throws ProductException;

    public List<Product> findProductByCategory(String category);

    public Page<Product> getAllProduct(String category, List<String>colours,List<String>sizes, Integer minPrice,
                                       Integer maxPrice,Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);
}
