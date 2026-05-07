package com.movements.feign;
import com.services.dtos.InventoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory", path = "/api/inventory")
public interface InventoryClient {

    @PutMapping("/{id}/stock")
    InventoryDTO updateStock(
            @PathVariable("id") Long id,
            @RequestParam("quantityChange") int quantityChange
    );
}