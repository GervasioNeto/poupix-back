package com.poupix.poupix.controller;

import com.poupix.poupix.dto.GroupDTO;
import com.poupix.poupix.dto.TransactionDTO;
import com.poupix.poupix.dto.UserDTO;
import com.poupix.poupix.entity.Group;
import com.poupix.poupix.service.GroupService;
import com.poupix.poupix.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/{userId}")
    public GroupDTO createGroup(@PathVariable Long userId, @RequestBody Group group) {
        Group savedGroup = groupService.createGroup(group, userId);
        return groupService.toDTO(savedGroup);
    }

    @GetMapping
    public List<GroupDTO> getAllGroups() {
        return groupService.getAllGroups().stream()
                .map(groupService::toDTO)
                .toList();
    }

    @PostMapping("/{groupId}/users/{userId}")
    public GroupDTO addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        Group updatedGroup = groupService.addUserToGroup(groupId, userId);
        return groupService.toDTO(updatedGroup);
    }

    // Lista usuários de um grupo
    @GetMapping("/{groupId}/users")
    public List<UserDTO> getGroupUsers(@PathVariable Long groupId) {
        return groupService.getGroupUsers(groupId)
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail()))
                .toList();
    }

    // Lista transações de um grupo
    @GetMapping("/{groupId}/transactions")
    public List<TransactionDTO> getGroupTransactions(@PathVariable Long groupId) {
        return groupService.getGroupTransactions(groupId)
                .stream()
                .map(transactionService::toDTO)  // você precisará injetar TransactionService
                .toList();
    }

    @PutMapping("/{groupId}")
    public GroupDTO updateGroup(@PathVariable Long groupId, @RequestBody Group group) {
        Group updatedGroup = groupService.updateGroup(groupId, group);
        return groupService.toDTO(updatedGroup);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId) {
        groupService.deleteGroup(groupId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}