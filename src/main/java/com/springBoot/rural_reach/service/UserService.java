package com.springBoot.rural_reach.service;

import com.springBoot.rural_reach.dto.UserDto;
import com.springBoot.rural_reach.entity.Permission;
import com.springBoot.rural_reach.entity.Role;
import com.springBoot.rural_reach.entity.User;
import com.springBoot.rural_reach.enums.ApprovalStatus;
import com.springBoot.rural_reach.exceptions.RoleException;
import com.springBoot.rural_reach.exceptions.UserNotFoundException;
import com.springBoot.rural_reach.repository.PermissionRepo;
import com.springBoot.rural_reach.repository.RoleRepo;
import com.springBoot.rural_reach.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PermissionRepo permissionRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //DISPLAYING all the Users
    public List<User> getAllUsers() {
        Role vendorRole = roleRepo.findByName("VENDOR").orElseThrow(RoleException::new);
        return userRepo.findByRole_id(vendorRole.getId());
    }

    //CREATING a User along with Roles and Permissions
    @Transactional //to make an operation atomic(either it completes fully or reverted if any process fails)
    public User createUser(UserDto userDto) {
        Role role = roleRepo.findById(userDto.getRoleId())
                .orElseThrow(RoleException::new);

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmailTd());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(role);

        Set<Permission> permissions = new HashSet<>();
        if (role.getPermissions() != null || !role.getPermissions().isEmpty()) {
            permissions.addAll(role.getPermissions());
        }
        role.setPermissions(permissions);
        return userRepo.save(user);
    }

    //APPROVING a Vendor
    public User approveVendor(Long userId,  String status) {
        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found !!"));
        try {
            ApprovalStatus approvalStatus = ApprovalStatus.valueOf(status.toUpperCase());
            user.setApprovalStatus(approvalStatus);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid approval status: " + status);
        }
        return userRepo.save(user);
    }

    //DELETING a user and its Permission
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User not found !!"));
        if (!"VENDOR".equalsIgnoreCase(user.getRole().getName())) {
            throw new SecurityException("Can only delete users with VENDOR role");
        }
        userRepo.deleteById(user.getId());
    }

    //    UPDATING a User
    @Transactional
    public User updateUser(Long id, UserDto userDto) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User not found !!"));
        Role role = roleRepo.findById(userDto.getRoleId())
                .orElseThrow(RoleException::new);
        if (userDto.getFirstName() != null)
            user.setFirstName(userDto.getFirstName());

        if (userDto.getLastName() != null)
            user.setLastName(userDto.getLastName());

        if (userDto.getEmailTd() != null)
            user.setEmail(userDto.getEmailTd());

        if (userDto.getPhoneNumber() != null)
            user.setPhoneNumber(userDto.getPhoneNumber());

        if (userDto.getPassword() != null)
            user.setPassword(userDto.getPassword());

        if (userDto.getId() != null) {
            Role roleOptional = roleRepo.findById(userDto.getId())
                    .orElseThrow(() -> new RoleException());
            user.setRole(roleOptional);
        }

        return user;
    }
}


