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

    @Column(columnDefinition = "LONGTEXT")
    private String imageUrl;

    public Inventory() {}

    public Inventory(String category, LocalDate expirationDate, Long id, String imageUrl, String lotNumber, Integer minStock, String name, Integer quantity, String unit, Double unitCost) {
        this.category = category;
        this.expirationDate = expirationDate;
        this.id = id;
        this.imageUrl = imageUrl;
        this.lotNumber = lotNumber;
        this.minStock = minStock;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.unitCost = unitCost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public Integer getMinStock() {
        return minStock;
    }

    public void setMinStock(Integer minStock) {
        this.minStock = minStock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }
}