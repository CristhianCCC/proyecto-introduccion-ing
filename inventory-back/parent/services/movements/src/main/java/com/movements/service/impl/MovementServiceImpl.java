package com.movements.service.impl;

import com.movements.entity.Movement;
import com.movements.feign.InventoryClient;
import com.movements.repository.MovementRepository;
import com.movements.service.MovementService;
import com.services.dtos.MovementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovementServiceImpl implements MovementService {

    private final MovementRepository repository;

    public MovementServiceImpl(MovementRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public InventoryClient inventoryClient;

    @Override
    public MovementDTO create(MovementDTO dto) {

        Movement m = new Movement();
        m.setProductId(dto.getProductId());
        m.setQuantityChange(dto.getQuantityChange());
        m.setType(dto.getType());
        Movement saved = repository.save(m);
        inventoryClient.updateStock(
                dto.getProductId(),
                dto.getQuantityChange()
        );
        dto.setId(saved.getId());
        dto.setDate(saved.getDate());

        return dto;
    }

    @Override
    public List<MovementDTO> findByProductId(Long productId) {
        return repository.findByProductId(productId)
                .stream()
                .map(m -> {
                    MovementDTO dto = new MovementDTO();
                    dto.setId(m.getId());
                    dto.setProductId(m.getProductId());
                    dto.setQuantityChange(m.getQuantityChange());
                    dto.setType(m.getType());
                    dto.setDate(m.getDate());
                    return dto;
                })
                .toList();
    }
}