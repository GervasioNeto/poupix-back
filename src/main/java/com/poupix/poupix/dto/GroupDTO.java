package com.poupix.poupix.dto;

import java.util.List;

public class GroupDTO {

    private Long id;
    private String name;
    private List<UserDTO> users;
    private String description;

    public GroupDTO() {}

    public GroupDTO(Long id, String name, List<UserDTO> users, String description) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.description = description;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
