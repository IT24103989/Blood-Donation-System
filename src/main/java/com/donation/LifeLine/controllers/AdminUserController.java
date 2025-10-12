package com.donation.LifeLine.controllers;

import com.donation.LifeLine.model.AdminUser;
import com.donation.LifeLine.services.AdminUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.net.URI;
import java.util.List;

@Controller // NOTE: Controller, not RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    /** Thymeleaf dashboard page */
    @GetMapping("/dashboard")
    public String dashboard() {
        return "Admin/admin-dashboard"; // Thymeleaf template in src/main/resources/templates/
    }

    // --- REST API endpoints for JS ---

    /** List all admin users */
    @GetMapping("/api/users")
    @ResponseBody
    public List<AdminUser> listUsers() {
        return adminUserService.listAll();
    }

    /** Get single user by ID */
    @GetMapping("/api/users/{id}")
    @ResponseBody
    public ResponseEntity<AdminUser> getUser(@PathVariable Long id) {
        return adminUserService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Create a new user */
    @PostMapping("/api/users")
    @ResponseBody
    public ResponseEntity<AdminUser> createUser(@Valid @RequestBody CreateUserRequest req) {
        AdminUser user = new AdminUser();
        user.setUsername(req.username);
        user.setEmail(req.email);
        user.setRole(req.role);
        user.setActive(req.active);
        AdminUser saved = adminUserService.create(user, req.password);
        return ResponseEntity.created(URI.create("/admin/api/users/" + saved.getId())).body(saved);
    }

    /** Update user */
    @PutMapping("/api/users/{id}")
    @ResponseBody
    public ResponseEntity<AdminUser> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest req) {
        AdminUser toUpdate = new AdminUser();
        toUpdate.setUsername(req.username);
        toUpdate.setEmail(req.email);
        toUpdate.setRole(req.role);
        toUpdate.setActive(req.active);
        AdminUser saved = adminUserService.update(id, toUpdate, req.password);
        return ResponseEntity.ok(saved);
    }

    /** Delete user */
    @DeleteMapping("/api/users/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminUserService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- DTO classes ---
    public static class CreateUserRequest {
        @jakarta.validation.constraints.NotBlank
        public String username;

        @jakarta.validation.constraints.Email
        @jakarta.validation.constraints.NotBlank
        public String email;

        @jakarta.validation.constraints.NotBlank
        public String password;

        public AdminUser.Role role = AdminUser.Role.MEDICAL_OFFICER;
        public boolean active = true;
    }

    public static class UpdateUserRequest {
        @jakarta.validation.constraints.NotBlank
        public String username;

        @jakarta.validation.constraints.Email
        @jakarta.validation.constraints.NotBlank
        public String email;

        public String password; // optional
        public AdminUser.Role role;
        public boolean active;
    }
}