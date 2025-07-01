package com.springBoot.rural_reach.controller;

import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/auth")
//@RequiredArgsConstructor
//public class AuthController {
//    private final AuthService authService;
//
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody User user) {
//        return authService.register(user);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
//        return authService.login(request);
//    }
//}

@RestController
@RequestMapping("/user")
public class UserController {
//    private final UserService userService;

//    @GetMapping("/profile")
//    public ResponseEntity<User> getProfile(Principal principal) {
//        return userService.getProfile(principal);
//    }

//    @PutMapping("/update")
//    public ResponseEntity<User> updateProfile(@RequestBody User updatedUser, Principal principal) {
//        return userService.updateProfile(updatedUser, principal);
//    }
}

//@RestController
//@RequestMapping("/vendor")
//@RequiredArgsConstructor
//public class VendorController {
//    private final ServiceListingService listingService;
//    private final OrderService orderService;
//
//    @PostMapping("/service")
//    @PreAuthorize("hasRole('VENDOR')")
//    public ResponseEntity<ServiceListing> addService(@RequestBody ServiceListing listing, Principal principal) {
//        return listingService.addService(listing, principal);
//    }
//
//    @GetMapping("/services")
//    @PreAuthorize("hasRole('VENDOR')")
//    public ResponseEntity<List<ServiceListing>> getOwnServices(Principal principal) {
//        return listingService.getOwnServices(principal);
//    }
//
//    @PutMapping("/service/{id}")
//    @PreAuthorize("hasRole('VENDOR')")
//    public ResponseEntity<?> updateService(@PathVariable Long id, @RequestBody ServiceListing updated) {
//        return listingService.updateService(id, updated);
//    }
//
//    @DeleteMapping("/service/{id}")
//    @PreAuthorize("hasRole('VENDOR')")
//    public ResponseEntity<?> deleteService(@PathVariable Long id) {
//        return listingService.deleteService(id);
//    }
//
//    @GetMapping("/orders")
//    @PreAuthorize("hasRole('VENDOR')")
//    public ResponseEntity<List<Order>> getOrders(Principal principal) {
//        return orderService.getVendorOrders(principal);
//    }
//}
//
//@RestController
//@RequestMapping("/services")
//@RequiredArgsConstructor
//public class ServiceController {
//    private final ServiceListingService listingService;
//
//    @GetMapping
//    public ResponseEntity<List<ServiceListing>> getAllServices() {
//        return listingService.getAllActiveServices();
//    }
//
//    @GetMapping("/category/{id}")
//    public ResponseEntity<List<ServiceListing>> getByCategory(@PathVariable Long id) {
//        return listingService.getByCategory(id);
//    }
//
//    @GetMapping("/location/{pincode}")
//    public ResponseEntity<List<ServiceListing>> getByLocation(@PathVariable String pincode) {
//        return listingService.getByLocation(pincode);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ServiceListing> getService(@PathVariable Long id) {
//        return listingService.getServiceById(id);
//    }
//}
//
//@RestController
//@RequestMapping("/orders")
//@RequiredArgsConstructor
//public class OrderController {
//    private final OrderService orderService;
//
//    @PostMapping
//    @PreAuthorize("hasRole('CUSTOMER')")
//    public ResponseEntity<Order> placeOrder(@RequestBody Order order, Principal principal) {
//        return orderService.placeOrder(order, principal);
//    }
//
//    @GetMapping
//    @PreAuthorize("hasRole('CUSTOMER')")
//    public ResponseEntity<List<Order>> getMyOrders(Principal principal) {
//        return orderService.getCustomerOrders(principal);
//    }
//
//    @PutMapping("/{id}/status")
//    @PreAuthorize("hasRole('VENDOR')")
//    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam String status) {
//        return orderService.updateOrderStatus(id, status);
//    }
//}
//
//@RestController
//@RequestMapping("/reviews")
//@RequiredArgsConstructor
//public class ReviewController {
//    private final ReviewService reviewService;
//
//    @PostMapping
//    @PreAuthorize("hasRole('CUSTOMER')")
//    public ResponseEntity<Review> addReview(@RequestBody Review review, Principal principal) {
//        return reviewService.addReview(review, principal);
//    }
//
//    @GetMapping("/service/{id}")
//    public ResponseEntity<List<Review>> getReviews(@PathVariable Long id) {
//        return reviewService.getReviewsForService(id);
//    }
//}
//
//@RestController
//@RequestMapping("/admin")
//@RequiredArgsConstructor
//public class AdminController {
//    private final AdminService adminService;
//
//    @GetMapping("/vendors")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<List<User>> getPendingVendors() {
//        return adminService.getPendingVendors();
//    }
//
//    @PutMapping("/approve/{userId}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> approveVendor(@PathVariable Long userId) {
//        return adminService.approveVendor(userId);
//    }
//
//    @GetMapping("/overview")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> getPlatformOverview() {
//        return adminService.getPlatformStats();
//    }
//}
