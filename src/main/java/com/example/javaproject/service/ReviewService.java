package com.example.javaproject.service;

import com.example.javaproject.dto.ReviewDto;
import com.example.javaproject.exception.definition.ProductNotFound;
import com.example.javaproject.exception.definition.ReviewNotFound;
import com.example.javaproject.mapper.ReviewMapper;
import com.example.javaproject.model.*;
import com.example.javaproject.model.security.User;
import com.example.javaproject.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    public Review findById(Long id) throws ReviewNotFound {
        Optional<Review> review =  reviewRepository.findById(id);
        if (review.isEmpty())
            throw new ReviewNotFound("Review not found");
        return review.get();
    }

    public void deleteReviewById(Long id) throws ReviewNotFound {
        Review review = findById(id);

        reviewRepository.delete(review);
    }

    public ReviewDto addReview(ReviewDto reviewDto) throws ProductNotFound {
        Review review = reviewMapper.mapToEntity(reviewDto);
        User user = userService.findById(review.getUser().getId());
        Product product = productService.findById(review.getProduct().getId());

        review.setUser(user);
        review.setProduct(product);

        return reviewMapper.mapToDto(reviewRepository.save(review));
    }

    public List<ReviewDto> findReviewsByProduct(Long productId) throws ReviewNotFound {
        List<Review> reviews =  reviewRepository.findReviewsByProductId(productId);
        if (reviews == null)
            throw new ReviewNotFound("Reviews not found");
        List <ReviewDto> reviewDtos = reviews.stream().map(review -> reviewMapper.mapToDto(review))
                .collect(Collectors.toList());
        return reviewDtos;
    }
}
