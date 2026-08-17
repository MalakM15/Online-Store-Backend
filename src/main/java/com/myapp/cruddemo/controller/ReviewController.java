package com.myapp.cruddemo.controller;

import com.myapp.cruddemo.entity.Review;
import com.myapp.cruddemo.service.ReviewService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


import java.util.List;


@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // GET /api/reviews
    @GetMapping("/products/{productId}")
    public List<Review> getReviewsByProductId (@PathVariable int productId) {
        return reviewService.getReviewsByProductId(productId);
    }

    @PostMapping("/products/{productId}")
    public Review createReview (@PathVariable int productId, @RequestBody Review review, Authentication authentication){
        
        return reviewService.createReview(productId,authentication.getName(), review.getComment());

    }

}
