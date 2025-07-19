package com.springBoot.rural_reach.repository;

import com.springBoot.rural_reach.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PermissionRepo extends JpaRepository<Permission,Long> {
    /**
     * @param: name -> Permission_Name
     * @return: Returns the permission if found or returns null
     */
    Optional<Permission> findByName(String name);
}