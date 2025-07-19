package com.springBoot.rural_reach.dataInitializer;

import com.springBoot.rural_reach.entity.Permission;
import com.springBoot.rural_reach.entity.Role;
import com.springBoot.rural_reach.entity.User;
import com.springBoot.rural_reach.repository.PermissionRepo;
import com.springBoot.rural_reach.repository.RoleRepo;
import com.springBoot.rural_reach.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PermissionRepo permissionRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        try{
            System.out.println("Database creation started");
            if (userRepo.count() == 0) {
                System.out.println("Creating default roles, permissions, and users...");

                Permission readPermission = permissionRepo.findByName("READ")
                        .orElseGet(() -> {
                            System.out.println("Creating READ permission");
                            return permissionRepo.save(new Permission("READ"));
                        });

                Permission createPermission = permissionRepo.findByName("CREATE")
                        .orElseGet(() -> {
                            System.out.println("Creating CREATE permission");
                            return permissionRepo.save(new Permission("CREATE"));
                        });
                Permission updatePermission = permissionRepo.findByName("UPDATE")
                        .orElseGet(() -> {
                            System.out.println("Creating UPDATE permission");
                            return permissionRepo.save(new Permission("UPDATE"));
                        });
                Permission deletePermission = permissionRepo.findByName("DELETE")
                        .orElseGet(() -> {
                            System.out.println("Creating DELETE permission");
                            return permissionRepo.save(new Permission("DELETE"));
                        });

                Set<Permission> defaultPermissions = new HashSet<>(Set.of(readPermission, createPermission, updatePermission, deletePermission));


                Role adminRole = roleRepo.findByName("ADMIN")
                        .orElseGet(() -> {
                            System.out.println("Creating role");
                            Role newrole = new Role();
                            newrole.setName("ADMIN");
                            newrole.setPermissions(defaultPermissions);
                            return roleRepo.save(newrole);
                        });

                User adminUser = new User();
                adminUser.setFirstName("New");
                adminUser.setLastName("Admin");
                adminUser.setPhoneNumber("1234567890");
                adminUser.setEmail("admin@example.com");
                adminUser.setPassword(passwordEncoder.encode("admin123"));
                adminUser.setRole(adminRole);
                userRepo.save(adminUser);
                System.out.println("Database initialization complete");
            }
        }
        catch (Exception e){
            System.out.println("Database Creation Failed");
            e.printStackTrace();
        }

    }
}
