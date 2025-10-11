package finalice.project.finalice.project.config;

import finalice.project.finalice.project.domain.AdminUser;
import finalice.project.finalice.project.domain.BloodInventory;
import finalice.project.finalice.project.repository.AdminUserRepository;
import finalice.project.finalice.project.repository.BloodInventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(AdminUserRepository users, BloodInventoryRepository inventory) {
        return args -> {
            if (users.count() == 0) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                AdminUser admin = new AdminUser();
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setRole(AdminUser.Role.CHEF_MEDICAL_OFFICER);
                admin.setPasswordHash(encoder.encode("admin123"));
                users.save(admin);
            }

            String[] types = {"A+","A-","B+","B-","AB+","AB-","O+","O-"};
            for (String t : types) {
                inventory.findByBloodType(t).orElseGet(() -> {
                    BloodInventory b = new BloodInventory();
                    b.setBloodType(t);
                    b.setUnitsAvailable(0);
                    return inventory.save(b);
                });
            }
        };
    }
}