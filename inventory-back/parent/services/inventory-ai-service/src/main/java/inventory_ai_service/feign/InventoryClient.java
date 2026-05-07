package inventory_ai_service.feign;

import com.services.dtos.InventoryDTO;
import com.services.dtos.MovementDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "inventory", path = "/api/inventory")
public interface InventoryClient {

    @GetMapping("/{id}")
    InventoryDTO getInventory(@PathVariable Long id);

}