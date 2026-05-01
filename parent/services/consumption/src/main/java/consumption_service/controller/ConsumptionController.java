package consumption_service.controller;
import com.services.dtos.ConsumptionDTO;
import consumption_service.service.ConsumptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consumption")
@CrossOrigin("*")
public class ConsumptionController {

    private final ConsumptionService service;

    public ConsumptionController(ConsumptionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ConsumptionDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsumptionDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ConsumptionDTO>> getByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(service.findByProductId(productId));
    }

    @PostMapping
    public ResponseEntity<ConsumptionDTO> create(@RequestBody ConsumptionDTO dto) {
        ConsumptionDTO saved = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}