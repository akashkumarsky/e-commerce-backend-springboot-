package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.model.Rating;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.request.RatingRequest;

import java.util.List;

public interface RatingServices {

    public Rating createRating(RatingRequest req, User user) throws ProductException;

    public List<Rating> getProductsRating(Long productId);
}
