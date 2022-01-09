package com.example.javaproject.repository;

import com.example.javaproject.model.Order;
import com.example.javaproject.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAll();

    @Query("select r from Review r join r.product p where p.id = :productId")
    List<Review> findReviewsByProductId(Long productId);
}
