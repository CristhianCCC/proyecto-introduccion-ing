package com.services.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;            // Nombre del insumo (Ej: Ácido acético)
    private String category;        // Reactivo, material, equipo
    private String unit;            // ml, g, unidades

    private Integer quantity;       // Cantidad disponible
    private Integer minStock;       // Stock mínimo

    private String lotNumber;       // Número de lote
    private LocalDate expirationDate; // Fecha de vencimiento

    private Double unitCost;        // Costo por unidad

    public Inventory() {}

    public Inventory(Long id, String name, String category, String unit,
                     Integer quantity, Integer minStock,
                     String lotNumber, LocalDate expirationDate,
                     Double unitCost) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.unit = unit;
        this.quantity = quantity;
        this.minStock = minStock;
        this.lotNumber = lotNumber;
        this.expirationDate = expirationDate;
        this.unitCost = unitCost;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Integer getMinStock() { return minStock; }
    public void setMinStock(Integer minStock) { this.minStock = minStock; }

    public String getLotNumber() { return lotNumber; }
    public void setLotNumber(String lotNumber) { this.lotNumber = lotNumber; }

    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }

    public Double getUnitCost() { return unitCost; }
    public void setUnitCost(Double unitCost) { this.unitCost = unitCost; }
}