package com.springBoot.rural_reach.controller;

import com.springBoot.rural_reach.dto.RoleDto;
import com.springBoot.rural_reach.entity.Role;
import com.springBoot.rural_reach.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @GetMapping("/all")
    public List<Role> getAlRoles(){
        return roleService.getAllRoles();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Role> createRole(@RequestBody RoleDto roleDto){
        Role createdRole = roleService.createRoleWithPermissions(roleDto);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }


    @PatchMapping("/update/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id,@RequestBody RoleDto roleDto){
        Role updatedRole = roleService.updateRole(id,roleDto);
        return ResponseEntity.ok(updatedRole);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
