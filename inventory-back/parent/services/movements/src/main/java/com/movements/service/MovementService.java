package com.movements.service;

import com.services.dtos.MovementDTO;

import java.util.List;

public interface MovementService {

    MovementDTO create(MovementDTO dto);

    List<MovementDTO> findByProductId(Long productId);
}