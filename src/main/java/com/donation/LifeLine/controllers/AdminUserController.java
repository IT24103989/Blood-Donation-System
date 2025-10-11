package finalice.project.finalice.project.controller;

import finalice.project.finalice.project.domain.AdminUser;
import finalice.project.finalice.project.service.AdminUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {
    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping
    public List<AdminUser> list() { return adminUserService.listAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<AdminUser> get(@PathVariable Long id) {
        return adminUserService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public static class CreateUserRequest {
        @jakarta.validation.constraints.NotBlank public String username;
        @jakarta.validation.constraints.Email @jakarta.validation.constraints.NotBlank public String email;
        @jakarta.validation.constraints.NotBlank public String password;
        public AdminUser.Role role = AdminUser.Role.MEDICAL_OFFICER;
        public boolean active = true;
    }

    @PostMapping
    public ResponseEntity<AdminUser> create(@Valid @RequestBody CreateUserRequest req) {
        AdminUser user = new AdminUser();
        user.setUsername(req.username);
        user.setEmail(req.email);
        user.setRole(req.role);
        user.setActive(req.active);
        AdminUser saved = adminUserService.create(user, req.password);
        return ResponseEntity.created(URI.create("/api/admin/users/" + saved.getId())).body(saved);
    }

    public static class UpdateUserRequest {
        @jakarta.validation.constraints.NotBlank public String username;
        @jakarta.validation.constraints.Email @jakarta.validation.constraints.NotBlank public String email;
        public String password; // optional
        public AdminUser.Role role;
        public boolean active;
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminUser> update(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest req) {
        AdminUser toUpdate = new AdminUser();
        toUpdate.setUsername(req.username);
        toUpdate.setEmail(req.email);
        toUpdate.setRole(req.role);
        toUpdate.setActive(req.active);
        AdminUser saved = adminUserService.update(id, toUpdate, req.password);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adminUserService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

