package com.services.dtos;

import java.time.LocalDate;

public class InventoryDTO {

    private Long id;
    private String name;
    private String category;
    private String unit;
    private Integer quantity;
    private Integer minStock;
    private String lotNumber;
    private LocalDate expirationDate;
    private Double unitCost;
    private String imageUrl;


    public InventoryDTO() {}

    public InventoryDTO(Long id, String name, String category, String unit,
                        Integer quantity, Integer minStock,
                        String lotNumber, LocalDate expirationDate,
                        Double unitCost, String imageUrl) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.unit = unit;
        this.quantity = quantity;
        this.minStock = minStock;
        this.lotNumber = lotNumber;
        this.expirationDate = expirationDate;
        this.unitCost = unitCost;
        this.imageUrl = imageUrl;
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

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}