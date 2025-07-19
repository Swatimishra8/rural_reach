package com.springBoot.rural_reach.repository;

import com.springBoot.rural_reach.entity.ServiceOffering;
import com.springBoot.rural_reach.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorServiceRepo extends JpaRepository<ServiceOffering, Long> {

    List<ServiceOffering> findByVendor(User vendor);

}

