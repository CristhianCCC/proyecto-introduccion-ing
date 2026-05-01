package consumption_service.service;

import com.services.dtos.ConsumptionDTO;

import java.util.List;

public interface ConsumptionService {

    List<ConsumptionDTO> findAll();

    ConsumptionDTO findById(Long id);

    List<ConsumptionDTO> findByProductId(Long productId);

    ConsumptionDTO save(ConsumptionDTO dto);

    void delete(Long id);
}