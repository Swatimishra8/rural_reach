package com.springBoot.rural_reach.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class UserDto {

    private Long id;

    @NotEmpty(message = "First name cannot be empty")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;

    @NotEmpty(message = "Phone number cannot be empty")
    private String phoneNumber;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email cannot be empty")
    private String emailTd;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotEmpty(message = "Role Required")
    private Long roleId;

}
