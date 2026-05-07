package com.services.dtos;

import java.time.LocalDate;

public class ConsumptionDTO {

    private Long id;
    private Long productId;
    private Integer quantity;
    private LocalDate date;

    public ConsumptionDTO() {}

    public ConsumptionDTO(Long id, Long productId, Integer quantity, LocalDate date) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.date = date;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}