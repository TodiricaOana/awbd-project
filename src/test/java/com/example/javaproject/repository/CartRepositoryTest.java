package com.example.javaproject.repository;

import com.example.javaproject.model.Cart;
import com.example.javaproject.model.Review;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("h2")
@Rollback(false)
@Slf4j
class CartRepositoryTest {

    private static final Long USERID = 1L;

    @Autowired
    private CartRepository cartRepository;

    @Test
    @DisplayName("Find cart by user id")
    public void findCartByUserId() {
        Cart cart = cartRepository.findCartByUserId(USERID);

        assertNotNull(cart);
    }
}