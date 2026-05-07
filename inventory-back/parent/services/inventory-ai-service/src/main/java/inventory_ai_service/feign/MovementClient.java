package inventory_ai_service.feign;
import com.services.dtos.MovementDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "movements", path = "/api/movements")
public interface MovementClient {

    // 🔥 Obtener movimientos por producto
    @GetMapping("/product/{productId}")
    List<MovementDTO> getMovements(@PathVariable Long productId);
}