package com.springBoot.rural_reach.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.springBoot.rural_reach.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    private String phoneNumber;

    private String address;

    private String pinCode;


    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
//    @JsonBackReference
    private Role role;

//    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.DETACH)
//    @JoinTable(
//            name = "users_permissions",
//            joinColumns = @JoinColumn(name = "users_id"),
//            inverseJoinColumns = @JoinColumn(name = "permissions_id")
//    )
//    private Set<Permission> permissions= new HashSet<>();

//    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
//    private List<ServiceOffering> serviceOfferings;
//
//    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
//    private List<Order> orders;
//
//    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
//    private List<Review> reviews;
}

