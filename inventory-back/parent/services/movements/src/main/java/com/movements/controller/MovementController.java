package com.movements.controller;
import com.movements.service.MovementService;
import com.services.dtos.MovementDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movements")
public class MovementController {

    private final MovementService service;

    public MovementController(MovementService service) {
        this.service = service;
    }

    @PostMapping
    public MovementDTO create(@RequestBody MovementDTO dto) {
        return service.create(dto);
    }

    @GetMapping("/product/{productId}")
    public List<MovementDTO> byProduct(@PathVariable Long productId) {
        return service.findByProductId(productId);
    }
}