package com.example.javaproject.repository;

import com.example.javaproject.model.Product;
import com.example.javaproject.model.ProductType;
import com.example.javaproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();

    @Query(value = "SELECT p FROM Product p WHERE p.productType = :productType")
    List<Product> filter(ProductType productType);

}
