package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.model.Product;
import com.sky.ecommerce.model.Review;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.repository.ProductRepository;
import com.sky.ecommerce.repository.ReviewRepository;
import com.sky.ecommerce.request.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements  ReviewService{

    private ReviewRepository reviewRepository;
    private ProductService productService;
    private ProductRepository productRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductService productService, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    public ReviewServiceImpl (){}

    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {

        Product product = productService.findProductById(req.getProductId());

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReview(req.getReview());
        review.setCreatedAt(LocalDateTime.now());

        productRepository.save(product);
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }
}
