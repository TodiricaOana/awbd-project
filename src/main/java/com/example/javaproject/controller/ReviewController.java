package com.example.javaproject.controller;

import com.example.javaproject.dto.ProductDto;
import com.example.javaproject.dto.ReviewDto;
import com.example.javaproject.exception.definition.ProductNotFound;
import com.example.javaproject.exception.definition.ReviewNotFound;
import com.example.javaproject.mapper.ReviewMapper;
import com.example.javaproject.model.ProductType;
import com.example.javaproject.model.Review;
import com.example.javaproject.service.ProductService;
import com.example.javaproject.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewMapper reviewMapper;

    @PostMapping("/add")
    public ResponseEntity<ReviewDto> addReview(@Valid @RequestBody ReviewDto reviewDto) throws ProductNotFound {
        return ResponseEntity
                .ok()
                .body(reviewService.addReview(reviewDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ReviewDto> updateReview(@Valid @RequestBody ReviewDto reviewDto, @PathVariable Long id) throws ReviewNotFound {
        return ResponseEntity
                .ok()
                .body(reviewService.updateReview(reviewDto, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ReviewDto> delete(@PathVariable Long id) throws ReviewNotFound {
        reviewService.deleteReviewById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{productId}")
    public ResponseEntity<List<ReviewDto>> getByProductType(@PathVariable Long productId) throws ReviewNotFound {
        return ResponseEntity
                .ok()
                .body(reviewService.findReviewsByProduct(productId));
    }
}
