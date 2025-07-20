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
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @GetMapping("/all")
    public ResponseEntity<List<RoleDto>> getAlRoles(){
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PostMapping("/create")
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto){
        RoleDto createdRole = roleService.createRoleWithPermissions(roleDto);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }


    @PatchMapping("/update/{id}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable Long id,@RequestBody RoleDto roleDto){
        RoleDto updatedRole = roleService.updateRole(id,roleDto);
        return ResponseEntity.ok(updatedRole);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
