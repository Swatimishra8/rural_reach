package com.springBoot.rural_reach.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServiceDto {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private Long vendorId;
    private Long categoryId;
}
