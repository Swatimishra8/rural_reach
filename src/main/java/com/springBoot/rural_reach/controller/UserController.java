package com.springBoot.rural_reach.controller;

import com.springBoot.rural_reach.dto.UserDto;
import com.springBoot.rural_reach.entity.User;
import com.springBoot.rural_reach.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    // Admin only: Get all vendors
    @GetMapping("/getVendors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getVendors() {
        List<UserDto> vendors = userService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }

    //todo - fetch customers

    // All authenticated users: Create user
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUser =  userService.createUser(userDto);
        return ResponseEntity.status(201).body(createdUser);
    }

    // All authenticated users: Update user
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto updatedUser =  userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    // Admin only: Delete vendor
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

//    @PatchMapping("update/removePermission/{id}")
//    public ResponseEntity<User> removeUserPermission(@PathVariable Long id, @RequestBody Long permname){
//        User updatedUser = userService.(id,permname);
//        return ResponseEntity.ok(updatedUser);
//    }

    //    @GetMapping("/profile")
//    public ResponseEntity<User> getProfile(Principal principal) {
//        return userService.getProfile(principal);
//    }

//    @PutMapping("/update")
//    public ResponseEntity<User> updateProfile(@RequestBody User updatedUser, Principal principal) {
//        return userService.updateProfile(updatedUser, principal);
//    }

    @PutMapping("/approve/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> approveVendor(@PathVariable Long userId, @RequestBody String status) {
        UserDto approvedUser =  userService.approveVendor(userId, status);
        return ResponseEntity.ok(approvedUser);
    }

}


