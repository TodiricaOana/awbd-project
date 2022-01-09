package com.example.javaproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "number_of_products")
    private Integer numberOfProducts;

    @Column(name = "product_type")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private List<Cart> carts;

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private List<Order> orders;

    @JsonIgnore
    @OneToMany(mappedBy="product")
    private List<Review> reviews;
}
