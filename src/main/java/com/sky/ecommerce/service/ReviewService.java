package com.sky.ecommerce.service;
import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.model.Review;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.request.ReviewRequest;

import java.text.ParseException;
import java.util.List;

public interface ReviewService {
    public Review createReview(ReviewRequest req, User user) throws ProductException;

    public List<Review> getAllReview(Long productId);
}
