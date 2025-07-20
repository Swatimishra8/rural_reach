package com.springBoot.rural_reach.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class RoleDto {

    @NotBlank(message = "Provide a Role Name")
    private String name;

    private Set<String> permissions;
}