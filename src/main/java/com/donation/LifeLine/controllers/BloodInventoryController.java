package finalice.project.finalice.project.controller;

import finalice.project.finalice.project.domain.BloodInventory;
import finalice.project.finalice.project.service.BloodInventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/inventory")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080"})
public class BloodInventoryController {

    private final BloodInventoryService bloodInventoryService;

    public BloodInventoryController(BloodInventoryService bloodInventoryService) {
        this.bloodInventoryService = bloodInventoryService;
    }

    @GetMapping
    public List<BloodInventory> getAllInventory() {
        return bloodInventoryService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodInventory> getInventoryById(@PathVariable Long id) {
        return bloodInventoryService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BloodInventory createOrUpdateInventory(@RequestBody BloodInventory inventory) {
        return bloodInventoryService.create(inventory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BloodInventory> updateInventory(
            @PathVariable Long id,
            @RequestBody BloodInventory inventory) {
        try {
            BloodInventory updated = bloodInventoryService.update(id, inventory);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable Long id) {
        bloodInventoryService.delete(id);
    }
}