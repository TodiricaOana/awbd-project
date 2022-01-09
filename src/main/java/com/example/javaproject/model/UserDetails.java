package com.example.javaproject.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_details")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "other_information")
    private String otherInformation;

    @OneToOne(mappedBy = "userDetails")
    private User user;
}
