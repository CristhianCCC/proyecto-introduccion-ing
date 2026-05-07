package com.movements.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movements")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Integer quantityChange; // + o -

    private String type; // SALE, PURCHASE, ADJUSTMENT

    private LocalDateTime date;

    public Movement() {
        this.date = LocalDateTime.now();
    }

    public Movement(LocalDateTime date, Long id, Long productId, Integer quantityChange, String type) {
        this.date = date;
        this.id = id;
        this.productId = productId;
        this.quantityChange = quantityChange;
        this.type = type;
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