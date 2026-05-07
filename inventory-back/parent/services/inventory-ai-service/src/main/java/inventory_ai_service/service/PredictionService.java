package inventory_ai_service.service;

import inventory_ai_service.dto.PredictionResponse;

public interface PredictionService {

    PredictionResponse predict(Long productId);

}