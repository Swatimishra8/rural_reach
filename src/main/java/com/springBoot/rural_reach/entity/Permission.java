package com.springBoot.rural_reach.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true,nullable = false)
    private String name;

    public Permission() {
    }

    public Permission(String name) {
        this.name = name;
    }
//
//    @ManyToMany(mappedBy = "permissions")
////    @JsonBackReference
//    private Set<Role> roles;
}

