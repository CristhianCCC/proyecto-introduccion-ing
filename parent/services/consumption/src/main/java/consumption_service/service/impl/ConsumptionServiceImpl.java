package consumption_service.service.impl;
import com.services.dtos.ConsumptionDTO;
import consumption_service.entity.Consumption;
import consumption_service.repository.ConsumptionRepository;
import consumption_service.service.ConsumptionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsumptionServiceImpl implements ConsumptionService {

    private final ConsumptionRepository repository;

    public ConsumptionServiceImpl(ConsumptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ConsumptionDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ConsumptionDTO findById(Long id) {
        return toDTO(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consumo no encontrado")));
    }

    @Override
    public List<ConsumptionDTO> findByProductId(Long productId) {
        return repository.findByProductId(productId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ConsumptionDTO save(ConsumptionDTO dto) {
        return toDTO(repository.save(toEntity(dto)));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    // Mapper
    private ConsumptionDTO toDTO(Consumption c) {
        return new ConsumptionDTO(
                c.getId(),
                c.getProductId(),
                c.getQuantity(),
                c.getDate()
        );
    }

    private Consumption toEntity(ConsumptionDTO d) {
        return new Consumption(
                d.getId(),
                d.getProductId(),
                d.getQuantity(),
                d.getDate()
        );
    }
}