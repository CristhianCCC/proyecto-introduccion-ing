package com.services.controller;
import com.services.dtos.InventoryDTO;
import com.services.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin("*")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    //  Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    //  Crear
    @PostMapping
    public ResponseEntity<InventoryDTO> create(@RequestBody InventoryDTO dto) {
        InventoryDTO saved = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    //  Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<InventoryDTO> update(@PathVariable Long id, @RequestBody InventoryDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    //  Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ⚠ Stock bajo
    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryDTO>> lowStock() {
        return ResponseEntity.ok(service.getLowStock());
    }

    //  Próximos a vencer
    @GetMapping("/expiring")
    public ResponseEntity<List<InventoryDTO>> expiringSoon() {
        return ResponseEntity.ok(service.getExpiringSoon());
    }
}