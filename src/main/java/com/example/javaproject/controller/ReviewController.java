package com.example.javaproject.controller;

import com.example.javaproject.dto.ReviewDto;
import com.example.javaproject.exception.definition.ProductNotFound;
import com.example.javaproject.exception.definition.ReviewNotFound;
import com.example.javaproject.model.Review;
import com.example.javaproject.service.ReviewService;
import com.example.javaproject.service.UserService;
import com.example.javaproject.service.security.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;

    @PostMapping
    public String create(@Valid @ModelAttribute("review") ReviewDto review, BindingResult bindingResult) throws ProductNotFound {
        if (bindingResult.hasErrors()) {
            return "reviewForm";
        }

        reviewService.addReview(review);
        return "redirect:/products/" + review.getProductId();
    }

    @GetMapping("/form/{productId}")
    public String reviewForm(Model model, @PathVariable("productId") Long productId) {
        ReviewDto review = new ReviewDto();
        review.setProductId(productId);

        String username = jpaUserDetailsService.getCurrentUser().getUsername();
        review.setUserId(userService.findUserByEmail(username).getId());
        model.addAttribute("review", review);
        return "reviewForm";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) throws ReviewNotFound {
        Review review = reviewService.findById(id);
        reviewService.deleteReviewById(id);
        return "redirect:/review/" + review.getProduct().getId();
    }

    @GetMapping("/{productId}")
    public String getReviews(@PathVariable Long productId, Model model) throws ReviewNotFound {
        model.addAttribute("reviews", reviewService.findReviewsByProduct(productId));
        return "reviewsByProduct";
    }

}
