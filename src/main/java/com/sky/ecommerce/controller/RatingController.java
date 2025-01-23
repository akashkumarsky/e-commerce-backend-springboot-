package com.sky.ecommerce.controller;

import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.exception.UserException;
import com.sky.ecommerce.model.Rating;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.request.RatingRequest;

import com.sky.ecommerce.service.RatingServices;
import com.sky.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private UserService userService;
    private RatingServices ratingServices;

    @Autowired
    public RatingController(UserService userService,RatingServices ratingServices) {
        this.ratingServices=ratingServices;
        this.userService=userService;

    }
    public RatingController(){}

    @PostMapping("/create")
    public ResponseEntity<Rating> createRatingHandler(@RequestBody RatingRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user=userService.findUserProfileByJwt(jwt);
        Rating rating=ratingServices.createRating(req, user);
        return new ResponseEntity<>(rating, HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductsReviewHandler(@PathVariable Long productId){

        List<Rating> ratings=ratingServices.getProductsRating(productId);
        return new ResponseEntity<>(ratings,HttpStatus.OK);
    }
}
