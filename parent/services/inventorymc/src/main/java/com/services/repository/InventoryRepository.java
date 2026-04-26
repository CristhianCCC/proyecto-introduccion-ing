package com.services.repository;
import com.services.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
public interface InventoryRepository extends JpaRepository<com.services.entity.Inventory, Long> {

    List<Inventory> findByQuantityLessThanEqual(Integer quantity);

    List<Inventory> findByExpirationDateBefore(LocalDate date);


}
