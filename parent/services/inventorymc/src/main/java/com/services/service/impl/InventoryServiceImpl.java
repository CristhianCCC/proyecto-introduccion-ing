package com.services.service.impl;
import com.services.dtos.InventoryDTO;
import com.services.entity.Inventory;
import com.services.repository.InventoryRepository;
import com.services.service.InventoryService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;

    public InventoryServiceImpl(InventoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<InventoryDTO> findAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public InventoryDTO findById(Long id) {
        return toDTO(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado")));
    }

    @Override
    public InventoryDTO save(InventoryDTO dto) {
        return toDTO(repository.save(toEntity(dto)));
    }

    @Override
    public InventoryDTO update(Long id, InventoryDTO dto) {
        Inventory inv = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado"));

        inv.setName(dto.getName());
        inv.setCategory(dto.getCategory());
        inv.setUnit(dto.getUnit());
        inv.setQuantity(dto.getQuantity());
        inv.setMinStock(dto.getMinStock());
        inv.setLotNumber(dto.getLotNumber());
        inv.setExpirationDate(dto.getExpirationDate());
        inv.setUnitCost(dto.getUnitCost());

        return toDTO(repository.save(inv));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<InventoryDTO> getLowStock() {
        return repository.findAll().stream()
                .filter(i -> i.getQuantity() <= i.getMinStock())
                .map(this::toDTO)
                .toList();
    }

    @Override
    public List<InventoryDTO> getExpiringSoon() {
        LocalDate today = LocalDate.now().plusDays(7);
        return repository.findByExpirationDateBefore(today)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Mapper
    private InventoryDTO toDTO(Inventory i) {
        return new InventoryDTO(
                i.getId(), i.getName(), i.getCategory(), i.getUnit(),
                i.getQuantity(), i.getMinStock(),
                i.getLotNumber(), i.getExpirationDate(), i.getUnitCost()
        );
    }

    private Inventory toEntity(InventoryDTO d) {
        return new Inventory(
                d.getId(), d.getName(), d.getCategory(), d.getUnit(),
                d.getQuantity(), d.getMinStock(),
                d.getLotNumber(), d.getExpirationDate(), d.getUnitCost()
        );
    }
}