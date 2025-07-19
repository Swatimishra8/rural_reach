package com.springBoot.rural_reach.service;

import com.springBoot.rural_reach.entity.ServiceOffering;
import com.springBoot.rural_reach.entity.User;
import com.springBoot.rural_reach.exceptions.ServiceNotFoundException;
import com.springBoot.rural_reach.exceptions.UserNotFoundException;
import com.springBoot.rural_reach.repository.UserRepo;
import com.springBoot.rural_reach.repository.VendorServiceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorServiceRepo serviceRepo;
    private final UserRepo userRepo;

    // List services owned by this vendor
    public List<ServiceOffering> getServicesByVendorId(Long vendorId) {
        User vendor = userRepo.findById(vendorId)
                .orElseThrow(() -> new UserNotFoundException("Vendor not found"));
        return serviceRepo.findByVendor(vendor);
    }

    // Create a service for a vendor
    public ServiceOffering createServiceForVendor(Long vendorId, ServiceOffering serviceDto) {
        User vendor = userRepo.findById(vendorId)
                .orElseThrow(() -> new UserNotFoundException("Vendor not found"));
        serviceDto.setVendor(vendor);
        serviceDto.setCreatedAt(LocalDateTime.now());
        serviceDto.setIsActive(true);
        return serviceRepo.save(serviceDto);
    }

    // Update a vendor’s own service
    public ServiceOffering updateServiceForVendor(Long vendorId, Long serviceId, ServiceOffering updated) {
        ServiceOffering service = serviceRepo.findById(serviceId)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found"));
        if (!service.getVendor().getId().equals(vendorId)) {
            throw new AccessDeniedException("Not authorized to update this service");
        }
        service.setTitle(updated.getTitle());
        service.setDescription(updated.getDescription());
        service.setPrice(updated.getPrice());
        return serviceRepo.save(service);
    }

    // Delete a vendor’s own service
    public void deleteServiceForVendor(Long vendorId, Long serviceId) {
        ServiceOffering service = serviceRepo.findById(serviceId)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found"));
        if (!service.getVendor().getId().equals(vendorId)) {
            throw new AccessDeniedException("Not authorized to delete this service");
        }
        serviceRepo.delete(service);
    }
}

