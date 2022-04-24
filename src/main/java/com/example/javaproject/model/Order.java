package com.example.javaproject.model;
import com.example.javaproject.model.security.User;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String status;

    @ManyToMany()
    @JoinTable(
            name = "product_order",
            joinColumns = @JoinColumn(name = "fk_order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "fk_product_id", referencedColumnName = "id"))
    private List<Product> products = new ArrayList<>();;

    @ManyToOne
    @JoinColumn(name="fk_user_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

}
