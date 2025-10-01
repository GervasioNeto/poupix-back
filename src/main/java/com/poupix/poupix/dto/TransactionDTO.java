package com.poupix.poupix.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionDTO {
    private Long id;
    private Long userId;
    private Double amount;
    private String type;
    private String category;
    private String description;
    private LocalDate date;
    private LocalDateTime createdAt;

    private Long groupId;

    public TransactionDTO(Long id, Long userId, Double amount, String type, String category, String description, LocalDate date, LocalDateTime createdAt, Long groupId) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.description = description;
        this.date = date;
        this.createdAt = createdAt;
        this.groupId = groupId;
    }

    public TransactionDTO() {
    }

    // Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }
}
