package com.services.dtos;

import java.time.LocalDateTime;

public class MovementDTO {

    private Long id;
    private Long productId;
    private Integer quantityChange;
    private String type;
    private LocalDateTime date;

    public MovementDTO(LocalDateTime date) {
        this.date = date;
    }

    public MovementDTO(LocalDateTime date, Long id, Long productId, Integer quantityChange, String type) {
        this.date = date;
        this.id = id;
        this.productId = productId;
        this.quantityChange = quantityChange;
        this.type = type;
    }

    public MovementDTO() {

    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantityChange() {
        return quantityChange;
    }

    public void setQuantityChange(Integer quantityChange) {
        this.quantityChange = quantityChange;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}