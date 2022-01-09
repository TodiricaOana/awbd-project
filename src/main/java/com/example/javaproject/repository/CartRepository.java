package com.example.javaproject.repository;

import com.example.javaproject.model.Cart;
import com.example.javaproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAll();

    @Query("select c from Cart c join c.user u where u.id = :userId")
    Cart findCartByUserId(Long userId);
}
