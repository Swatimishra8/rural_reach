package com.springBoot.rural_reach.service;

import com.springBoot.rural_reach.entity.Permission;
import com.springBoot.rural_reach.exceptions.PermissionException;
import com.springBoot.rural_reach.repository.PermissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepo permissionRepo;

    //    GETTING all permissions
    public List<Permission> getAllPermissions() {
        return permissionRepo.findAll();
    }

    // GETTING A PARTICULAR PERMISSION
    public Permission getThePermission(Long id) {
        return permissionRepo.findById(id).orElseThrow(() -> new PermissionException());
    }

    //    CREATING new permission
    public Permission createPermission(Permission permission) {
        Permission resPermission = permissionRepo.findByName(permission.getName())
                .orElseGet(() -> {
                    Permission newPermission = new Permission();
                    newPermission.setName(permission.getName());
                    return permissionRepo.save(newPermission);
                });
        return resPermission;
    }

    //     Updating A Permission
    public Permission updatePermission(Long id, Permission newName) {
        Permission existingPermission = permissionRepo.findById(id)
                .orElseThrow(() -> new PermissionException());
        existingPermission.setName(newName.getName());
        return permissionRepo.save(existingPermission);
    }

    //    Deleting A Permission
    public void deletePermission(Long id) {
        Permission permission = permissionRepo.findById(id).
                orElseThrow(() -> new PermissionException());
        permissionRepo.delete(permission);
    }
}
