package com.services.service.impl;

import com.services.dtos.InventoryDTO;
import com.services.entity.Inventory;
import com.services.repository.InventoryRepository;
import com.services.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;

    public InventoryServiceImpl(InventoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<InventoryDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryDTO findById(Long id) {
        return toDTO(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado")));
    }

    @Override
    public InventoryDTO save(InventoryDTO dto) {

        // 🔥 VALIDACIÓN: evitar productos duplicados por nombre
        if (repository.existsByName(dto.getName())) {
            throw new InventoryAlreadyExistsException("El producto ya existe. Debes actualizar el stock.");
        }

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
        inv.setImageUrl(dto.getImageUrl());

        return toDTO(repository.save(inv));
    }

    // 🔥 NUEVO: método para actualizar stock (base para IA)
    public InventoryDTO updateStock(Long id, int quantityChange) {
        Inventory inventory = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        int newQuantity = inventory.getQuantity() + quantityChange;
        if (newQuantity < 0) {
            throw new RuntimeException("Stock insuficiente");
        }
        inventory.setQuantity(newQuantity);
        Inventory updated = repository.save(inventory);
        return toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<InventoryDTO> getLowStock() {
        return repository.findAll()
                .stream()
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

    // =========================
    // MAPPERS
    // =========================

    private InventoryDTO toDTO(Inventory i) {
        return new InventoryDTO(
                i.getId(),
                i.getName(),
                i.getCategory(),
                i.getUnit(),
                i.getQuantity(),
                i.getMinStock(),
                i.getLotNumber(),
                i.getExpirationDate(),
                i.getUnitCost(),
                i.getImageUrl()
        );
    }

    private Inventory toEntity(InventoryDTO d) {
        Inventory inv = new Inventory();
        inv.setId(d.getId());
        inv.setName(d.getName());
        inv.setCategory(d.getCategory());
        inv.setUnit(d.getUnit());
        inv.setQuantity(d.getQuantity());
        inv.setMinStock(d.getMinStock());
        inv.setLotNumber(d.getLotNumber());
        inv.setExpirationDate(d.getExpirationDate());
        inv.setUnitCost(d.getUnitCost());
        inv.setImageUrl(d.getImageUrl());
        return inv;
    }

    // =========================
    // EXCEPCIÓN PERSONALIZADA
    // =========================

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class InventoryAlreadyExistsException extends RuntimeException {
        public InventoryAlreadyExistsException(String message) {
            super(message);
        }
    }
}