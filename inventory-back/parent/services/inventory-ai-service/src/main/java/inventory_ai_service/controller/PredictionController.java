package inventory_ai_service.controller;

import inventory_ai_service.dto.PredictionResponse;
import inventory_ai_service.service.PredictionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class PredictionController {

    private final PredictionService predictionService;

    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @GetMapping("/predict/{id}")
    public PredictionResponse predict(@PathVariable Long id) {
        return predictionService.predict(id);
    }
}