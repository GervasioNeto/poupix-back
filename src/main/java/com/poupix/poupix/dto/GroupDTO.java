package com.poupix.poupix.dto;

import com.poupix.poupix.entity.Group;

import java.util.List;

public class GroupDTO {

    private Long id;
    private String uuid;
    private String name;
    private List<GroupMemberDTO> members;
    private String description;


    public GroupDTO() {}

    public GroupDTO(Long id, String uuid, String name, List<GroupMemberDTO> members, String description) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.members = members;
        this.description = description;
    }

    public static GroupDTO fromEntity(Group group) {

        List<GroupMemberDTO> members = group.getGroupMembers().stream()
                .map(gm -> new GroupMemberDTO(
                        gm.getUser().getId(),
                        gm.getUser().getName(),
                        gm.getUser().getEmail(),
                        gm.getRole().name()
                ))
                .toList();

        return new GroupDTO(
                group.getId(),
                group.getUuid(),
                group.getName(),
                members,
                group.getDescription()
        );
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


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

    public List<GroupMemberDTO> getMembers() {
        return members;
    }

    public void setMembers(List<GroupMemberDTO> members) {
        this.members = members;
    }
}
