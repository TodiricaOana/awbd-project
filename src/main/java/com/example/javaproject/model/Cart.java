package com.example.javaproject.model;
import com.example.javaproject.model.security.User;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany()
    @JoinTable(
            name = "product_cart",
            joinColumns = @JoinColumn(name = "fk_cart_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "fk_product_id", referencedColumnName = "id"))
    private List<Product> products = new ArrayList<>();;

    @OneToOne(mappedBy = "cart")
    private User user;

}
