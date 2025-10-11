package finalice.project.finalice.project.service;

import finalice.project.finalice.project.domain.AdminUser;
import finalice.project.finalice.project.repository.AdminUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AdminUserService(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    public List<AdminUser> listAll() {
        return adminUserRepository.findAll();
    }

    public Optional<AdminUser> getById(Long id) {
        return adminUserRepository.findById(id);
    }

    public AdminUser create(AdminUser user, String rawPassword) {
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        return adminUserRepository.save(user);
    }

    public AdminUser update(Long id, AdminUser updated, String newRawPasswordOrNull) {
        AdminUser existing = adminUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("AdminUser not found"));
        existing.setUsername(updated.getUsername());
        existing.setEmail(updated.getEmail());
        existing.setRole(updated.getRole());
        existing.setActive(updated.isActive());
        if (newRawPasswordOrNull != null && !newRawPasswordOrNull.isBlank()) {
            existing.setPasswordHash(passwordEncoder.encode(newRawPasswordOrNull));
        }
        return adminUserRepository.save(existing);
    }

    public void delete(Long id) {
        adminUserRepository.deleteById(id);
    }
}