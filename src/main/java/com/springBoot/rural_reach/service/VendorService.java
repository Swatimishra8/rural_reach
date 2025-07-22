package com.springBoot.rural_reach.service;

import com.springBoot.rural_reach.dto.ServiceDto;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorServiceRepo serviceRepo;
    private final UserRepo userRepo;

    // List services owned by this vendor
    public List<ServiceDto> getServicesByVendorId(Long vendorId) {
        User vendor = userRepo.findById(vendorId)
                .orElseThrow(() -> new UserNotFoundException("Vendor not found"));
        return serviceRepo.findByVendor(vendor)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Create a service for a vendor
    public ServiceDto createServiceForVendor(Long vendorId, ServiceDto serviceDto) {
        User vendor = userRepo.findById(vendorId)
                .orElseThrow(() -> new UserNotFoundException("Vendor not found"));
        ServiceOffering newService = new ServiceOffering();
        service.setTitle(serviceDto.getTitle());
        service.setDescription(serviceDto.getDescription());
        service.setPrice(serviceDto.getPrice());
        service.setIsActive(true);
        service.setCreatedAt(LocalDateTime.now());
        service.setVendor(vendor);
        ServiceOffering saved = serviceRepo.save(newService);
        return toDto(saved);
    }

    // Update a vendor’s own service
    public ServiceDto updateServiceForVendor(Long vendorId, Long serviceId, ServiceDto updated) {
        ServiceOffering service = serviceRepo.findById(serviceId)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found"));
        if (!service.getVendor().getId().equals(vendorId)) {
            throw new AccessDeniedException("Not authorized to update this service");
        }
        service.setTitle(updated.getTitle());
        service.setDescription(updated.getDescription());
        service.setPrice(updated.getPrice());
        service.setIsActive(updated.getIsActive());
        ServiceOffering updatedService = serviceRepo.save(service);
        return toDto(updatedService);
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

    public ServiceDto toDto(ServiceOffering service) {
        ServiceDto dto = new ServiceDto();
        dto.setId(service.getId());
        dto.setTitle(service.getTitle());
        dto.setDescription(service.getDescription());
        dto.setPrice(service.getPrice());
        dto.setIsActive(service.getIsActive());
        dto.setCreatedAt(service.getCreatedAt());
        if (service.getVendor() != null) {
            dto.setVendorId(service.getVendor().getId());
        }
        if (service.getCategory() != null) {
            dto.setCategoryId(service.getCategory().getId());
        }
        return dto;
    }

}

