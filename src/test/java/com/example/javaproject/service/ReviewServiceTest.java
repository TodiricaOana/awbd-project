package com.example.javaproject.service;

import com.example.javaproject.dto.ReviewDto;
import com.example.javaproject.exception.definition.ProductNotFound;
import com.example.javaproject.exception.definition.ReviewNotFound;
import com.example.javaproject.mapper.ReviewMapper;
import com.example.javaproject.model.Product;
import com.example.javaproject.model.Review;
import com.example.javaproject.model.security.User;
import com.example.javaproject.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    private static final Long ID = 1L;
    private static final String TEXT = "dummyText";
    private static final String EMAIL = "dummy@gmail.com";
    private static final String NAME = "dummyName";

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @Test
    @DisplayName("Create review - works")
    void create_works() throws ProductNotFound {
        ReviewDto reviewDto = getReviewDto();
        Review review = getReview();

        Review savedReview = getReview();
        savedReview.setId(ID);

        ReviewDto returnedReviewDto = getReviewDto();
        returnedReviewDto.setId(ID);

        when(reviewMapper.mapToEntity(reviewDto)).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(savedReview);
        when(reviewMapper.mapToDto(savedReview)).thenReturn(returnedReviewDto);

        ReviewDto result = reviewService.addReview(reviewDto);

        assertNotNull(result);
        assertEquals(returnedReviewDto.getText(), result.getText());
        assertEquals(returnedReviewDto.getId(), result.getId());
    }

    @Test
    @DisplayName("delete review- works")
    void delete_works() throws ReviewNotFound {
        Review review = getReview();
        review.setId(ID);
        Optional<Review> optionalReview = Optional.of(review);

        when(reviewRepository.findById(ID)).thenReturn(optionalReview);

        reviewService.deleteReviewById(ID);

        verify(reviewRepository, times(1)).delete(review);
    }

    @Test
    @DisplayName("Get reviews by product - works")
    void getAll_works() throws ReviewNotFound {
        List<Review> reviews = List.of(getReview());
        List<ReviewDto> reviewsDto =  List.of(getReviewDto());

        when(reviewRepository.findReviewsByProductId(ID)).thenReturn(reviews);
        when(reviewMapper.mapToDto(reviews.get(0))).thenReturn(reviewsDto.get(0));

        List<ReviewDto> result = reviewService.findReviewsByProduct(ID);

        assertNotNull(result);
        assertEquals(reviewsDto.get(0).getText(), result.get(0).getText());
        assertEquals(reviewsDto.get(0).getId(), result.get(0).getId());
    }

    private ReviewDto getReviewDto() {
        return ReviewDto.builder()
                .text(TEXT)
                .build();
    }

    private Review getReview() {
        Review review = new Review();
        review.setText(TEXT);
        User user = getUser();
        Product product = getProduct();
        review.setUser(user);
        review.setProduct(product);

        return review;
    }

    private Product getProduct() {
        Product product = new Product();
        product.setName(NAME);

        return product;
    }

    private User getUser() {
        User user = new User();
        user.setEmail(EMAIL);

        return user;
    }
}