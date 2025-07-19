package com.springBoot.rural_reach.service;

import com.springBoot.rural_reach.customAnnotation.Loggable;
import com.springBoot.rural_reach.customAnnotation.TrackMethodExecutionTime;
import com.springBoot.rural_reach.dto.RoleDto;
import com.springBoot.rural_reach.entity.Permission;
import com.springBoot.rural_reach.entity.Role;
import com.springBoot.rural_reach.entity.User;
import com.springBoot.rural_reach.exceptions.RoleException;
import com.springBoot.rural_reach.repository.PermissionRepo;
import com.springBoot.rural_reach.repository.RoleRepo;
import com.springBoot.rural_reach.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PermissionRepo permissionRepo;

// DISPLAYING all the roles
    @TrackMethodExecutionTime
    @Loggable
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

//  CREATING A Role
    @Transactional
    @TrackMethodExecutionTime
    @Loggable
    public Role createRoleWithPermissions(RoleDto roleDto) {
        return roleRepo.findByName(roleDto.getName())
                .orElseGet(() -> {
                    Role newrole = new Role();
                    newrole.setName(roleDto.getName());
                    Set<Permission> permissions = new HashSet<>();
                    for (String permissionName : roleDto.getPermissions()) {
                        Permission permission = permissionRepo.findByName(permissionName)
                                .orElseGet(() -> {
                                    Permission newPermission = new Permission();
                                    newPermission.setName(permissionName);
                                    return permissionRepo.save(newPermission);
                                });
                        permissions.add(permission);
                    }
                    newrole.setPermissions(permissions);
                    return roleRepo.save(newrole);
                });
    }

    // UPDATING A Role
    @Transactional
    public Role updateRole(Long id, RoleDto roleDto) {
        Role existingRole = roleRepo.findByName(roleDto.getName())
                .orElseThrow(() -> new RoleException());

        existingRole.setName(roleDto.getName());

        if (!roleDto.getPermissions().isEmpty()) {
            Set<Permission> permissions = new HashSet<>();
            for (String permissionName : roleDto.getPermissions()) {
                Permission permission = permissionRepo.findByName(permissionName)
                        .orElseGet(() -> {
                            Permission newPermission = new Permission();
                            newPermission.setName(permissionName);
                            return permissionRepo.save(newPermission);
                        });
                permissions.add(permission);
            }
            existingRole.setPermissions(permissions);
        }
        return roleRepo.save(existingRole);
    }

    //  DELETING A Role
    @Transactional
    public void deleteRole(Long id) {
        Optional<Role> optionalRole = roleRepo.findById(id);

        if (optionalRole.isPresent()) {
            for (User user : userRepo.findByRole_id(id)) {
                user.setRole(null);
                userRepo.save(user);
            }
            roleRepo.deleteById(id);
        } else {
            throw new RoleException();
        }
    }

    //  UPDATING a role
//    @Transactional
//    public Role updateRole(String roleName, Role updateRole) {
//        Role existingRole = roleRepo.findByName(roleName)
//                .orElseThrow(() -> new RoleException());
//
//        existingRole.setName(updateRole.getName());
//
//        existingRole.getPermissions().clear();
//
//        existingRole.getPermissions().addAll(updateRole.getPermissions());
//
//        return roleRepo.save(existingRole);
//
//    }
}

