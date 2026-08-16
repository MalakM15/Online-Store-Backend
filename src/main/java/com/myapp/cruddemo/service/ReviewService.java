package com.myapp.cruddemo.service;

import org.springframework.stereotype.Service;

import com.myapp.cruddemo.dao.ProductRepository;
import com.myapp.cruddemo.dao.ReviewRepository;
import com.myapp.cruddemo.dao.UserRepository;
import com.myapp.cruddemo.entity.Product;
import com.myapp.cruddemo.entity.Review;
import com.myapp.cruddemo.entity.User;

import com.myapp.cruddemo.exception.ResourceNotFoundException;
import java.util.List;
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository, UserRepository userRepository){
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<Review> getReviewsByProductId(int productId){

        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }

        return reviewRepository.findByProductId(productId);
    }
    public Review createReview(int productId, String email, String comment) {

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        Review review = new Review(comment);
        review.setProduct(product);
        review.setUser(user);
        return reviewRepository.save(review);
    }


}
