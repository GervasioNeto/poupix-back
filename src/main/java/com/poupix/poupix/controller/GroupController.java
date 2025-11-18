package com.poupix.poupix.controller;

import com.poupix.poupix.dto.GroupDTO;
import com.poupix.poupix.dto.GroupMemberDTO;
import com.poupix.poupix.dto.TransactionDTO;
import com.poupix.poupix.dto.UserDTO;
import com.poupix.poupix.entity.Group;
import com.poupix.poupix.entity.Transaction;
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

//    @PostMapping("/{groupId}/transactions")
//    public TransactionDTO createTransaction(@PathVariable Long groupId, @RequestBody TransactionDTO transactionDTO) {
//        var transaction = transactionService.createTransaction(groupId, transactionDTO);
//        return transactionService.toDTO(transaction);
//    }

    @PostMapping("/{groupId}/users/{userId}")
    public GroupDTO addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        Group updatedGroup = groupService.addUserToGroup(groupId, userId);
        return groupService.toDTO(updatedGroup);
    }

    @PostMapping("/{groupId}/transactions")
    public ResponseEntity<TransactionDTO> createTransaction(
            @PathVariable Long groupId,
            @RequestBody TransactionDTO transactionDTO) {

        Transaction transaction = transactionService.createTransaction(transactionDTO, groupId);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.toDTO(transaction));
    }

    @PostMapping("/{groupId}/users/email")
    public ResponseEntity<GroupDTO> addUserToGroupByEmail(@PathVariable Long groupId, @RequestBody UserDTO userDTO) {
        Group updatedGroup = groupService.addUserToGroupByEmail(groupId, userDTO.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(groupService.toDTO(updatedGroup));
    }

    @GetMapping("/{groupId}/users")
    public List<UserDTO> getGroupUsers(@PathVariable Long groupId) {
        return groupService.getGroupUsers(groupId)
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail()))
                .toList();
    }

    @GetMapping("/{groupId}/members")
    public List<GroupMemberDTO> getGroupMembers(@PathVariable Long groupId) {
        return groupService.getGroupMembers(groupId).stream()
                .map(m -> new GroupMemberDTO(
                        m.getUser().getId(),
                        m.getUser().getName(),
                        m.getUser().getEmail(),
                        m.getRole().name()
                ))
                .toList();
    }

    @GetMapping
    public List<GroupDTO> getAllGroups() {
        return groupService.getAllGroups().stream()
                .map(groupService::toDTO)
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

    @DeleteMapping("/{groupId}/users/{userId}")
    public ResponseEntity<GroupDTO> removeUserFromGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        Group updatedGroup = groupService.removeUserFromGroup(groupId, userId);
        return ResponseEntity.ok(groupService.toDTO(updatedGroup));
    }

    @PostMapping("/{groupId}/users/{userId}/promote")
    public ResponseEntity<GroupDTO> promoteUser(@PathVariable Long groupId, @PathVariable Long userId) {
        groupService.promoteUser(groupId, userId);
        Group updatedGroup = groupService.getGroupById(groupId);
        return ResponseEntity.ok(groupService.toDTO(updatedGroup));
    }

    @GetMapping("/busca")
    public List<GroupDTO> searchGroups(@RequestParam("q") String query) {
        return groupService.searchGroups(query)
                .stream()
                .map(groupService::toDTO)
                .toList();
    }
}