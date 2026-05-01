package consumption_service.repository;
import consumption_service.entity.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {

    List<Consumption> findByProductId(Long productId);
}