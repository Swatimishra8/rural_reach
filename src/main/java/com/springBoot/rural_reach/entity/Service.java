package com.springBoot.rural_reach.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 500)
    private String description;

    private Double price;

    private Boolean isActive;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private User vendor;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ServiceCategory category;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    private List<Review> reviews;
}

