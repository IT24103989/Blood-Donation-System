package finalice.project.finalice.project.repository;

import finalice.project.finalice.project.domain.BloodInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BloodInventoryRepository extends JpaRepository<BloodInventory, Long> {
    Optional<BloodInventory> findByBloodType(String bloodType);
    boolean existsByBloodType(String bloodType);
}


