package com.poupix.poupix.dto;

import com.poupix.poupix.entity.Group;

import java.util.List;

public class GroupDTO {

    private Long id;
    private String uuid;
    private String name;
    private List<UserDTO> users;
    private String description;

    public GroupDTO() {}

    public GroupDTO(Long id, String uuid, String name, List<UserDTO> users, String description) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.users = users;
        this.description = description;
    }

    public static GroupDTO fromEntity(Group group) {
        List<UserDTO> users = group.getUsers().stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail()))
                .toList();

        return new GroupDTO(
                group.getId(),
                group.getUuid(), // mapeando o UUID
                group.getName(),
                users,
                group.getDescription()
        );
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
