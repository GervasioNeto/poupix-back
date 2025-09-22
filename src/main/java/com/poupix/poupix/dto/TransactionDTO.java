package com.poupix.poupix.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private Long userId;
    private Double amount;
    private String type;
    private String category;
    private String description;
    private LocalDate date;
    private LocalDateTime createdAt;

    public TransactionDTO(Long id, Long userId, Double amount, String type, String category, String description, LocalDate date, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.description = description;
        this.date = date;
        this.createdAt = createdAt;
    }

    // Getters e setters
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Double getAmount() { return amount; }
    public String getType() { return type; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
