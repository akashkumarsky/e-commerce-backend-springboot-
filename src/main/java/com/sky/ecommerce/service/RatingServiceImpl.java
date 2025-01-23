package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.model.Product;
import com.sky.ecommerce.model.Rating;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.repository.RatingRepository;
import com.sky.ecommerce.request.RatingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingServices {

    private RatingRepository ratingRepository;
    private ProductService productService;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository,ProductService productService) {
        this.ratingRepository=ratingRepository;
        this.productService=productService;
    }

    @Override
    public Rating createRating(RatingRequest req,User user) throws ProductException {

        Product product=productService.findProductById(req.getProductId());

        Rating rating=new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {
        // TODO Auto-generated method stub
        return ratingRepository.getAllProductsRating(productId);
    }

}
