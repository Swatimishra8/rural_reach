package com.springBoot.rural_reach.controller;

import com.springBoot.rural_reach.entity.ServiceOffering;
import com.springBoot.rural_reach.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vendors/{vendorId}/services")
@RequiredArgsConstructor
public class VendorServiceController {

    @Autowired
    private final VendorService service;

    // Anyone can view services of any vendor
    @GetMapping
    public ResponseEntity<List<ServiceOffering>> getVendorServices(@PathVariable Long vendorId) {
        List<ServiceOffering> list = service.getServicesByVendorId(vendorId);
        return ResponseEntity.ok(list);
    }

    //  Only the vendor with matching ID can create services
    @PostMapping
    @PreAuthorize("hasRole('VENDOR') and #vendorId == principal.id")
    public ResponseEntity<ServiceOffering> createService(
            @PathVariable Long vendorId,
            @RequestBody ServiceOffering newServiceOffering
    ) {
        ServiceOffering created = service.createServiceForVendor(vendorId, newServiceOffering);
        return ResponseEntity.status(201).body(created);
    }

    // Only the owning vendor can update their service
    @PutMapping("/{serviceId}")
    @PreAuthorize("hasRole('VENDOR') and #vendorId == principal.id")
    public ResponseEntity<ServiceOffering> updateService(
            @PathVariable Long vendorId,
            @PathVariable Long serviceId,
            @RequestBody ServiceOffering updated
    ) {
        ServiceOffering updatedServiceOffering = service.updateServiceForVendor(vendorId, serviceId, updated);
        return ResponseEntity.ok(updatedServiceOffering);
    }

    // Only the owning vendor can delete their service
    @DeleteMapping("/{serviceId}")
    @PreAuthorize("hasRole('VENDOR') and #vendorId == principal.id")
    public ResponseEntity<Void> deleteService(
            @PathVariable Long vendorId,
            @PathVariable Long serviceId
    ) {
        service.deleteServiceForVendor(vendorId, serviceId);
        return ResponseEntity.noContent().build();
    }
}
