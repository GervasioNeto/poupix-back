package com.poupix.poupix.controller;

import com.poupix.poupix.dto.GroupDTO;
import com.poupix.poupix.dto.TransactionDTO;
import com.poupix.poupix.dto.UserDTO;
import com.poupix.poupix.entity.User;
import com.poupix.poupix.service.TransactionService;
import com.poupix.poupix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{userId}/transactions")
    public ResponseEntity<List<TransactionDTO>> getUserTransactions(@PathVariable Long userId) {
        List<TransactionDTO> transactions = transactionService.getUserTransactions(userId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{userId}/groups")
    public ResponseEntity<List<GroupDTO>> getUserGroups(@PathVariable Long userId) {
        List<GroupDTO> groups = userService.getUserGroups(userId);
        return ResponseEntity.ok(groups);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        return ResponseEntity.ok(userService.toDTO(savedUser));
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(userService::toDTO)
                .toList();
    }

    @PutMapping("/{id}")
    private UserDTO updateUser(@PathVariable Long id, @RequestBody User user){
        return  userService.updateUser(id, user);
    }

    @GetMapping("/{id}")
    private ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        return userService.getUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
