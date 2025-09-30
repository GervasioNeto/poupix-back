package com.poupix.poupix.dto;


import com.poupix.poupix.entity.Transaction;

import java.util.List;

public class UserDTO {

    private Long id;
    private String name;
    private String email;

    private List<Transaction> transactions;

    public UserDTO() {}

    public UserDTO(Long id, String name, String email, List<Transaction> transactions) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.transactions = transactions;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Transaction> getTransactions() { return transactions; }
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }
}