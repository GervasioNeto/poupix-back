package com.poupix.poupix.controller;

import com.poupix.poupix.dto.TransactionDTO;
import com.poupix.poupix.entity.Transaction;
import com.poupix.poupix.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

//    public TransactionController(TransactionService transactionService) {
//        this.transactionService = transactionService;
//    }

//    @GetMapping
//    public List<Transaction> getAllTransactions() {
//        return transactionService.getAllTransactions();
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id)
                .map(transaction -> new ResponseEntity<>(transaction, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

//    @PostMapping("/group/{groupId}")
//    public Transaction saveTransaction(
//            @PathVariable Long userId,
//            @PathVariable Long groupId,
//            @RequestBody Transaction transaction) {
//        return transactionService.saveTransaction(userId, groupId, transaction);
//    }

//    @PutMapping("/{id}")
//    public Transaction updateTransactionByUserId(@PathVariable Long id, @RequestBody Transaction newTransaction) {
//        Transaction transaction = transactionService.updateTransactionByUserId(id, newTransaction);
//        return ResponseEntity.ok(transaction).getBody();
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody Transaction newTransaction) {
        Transaction updated = transactionService.updateTransaction(id, newTransaction);
        return ResponseEntity.ok(updated);
    }

//    @PutMapping("/{id}")
//    public Transaction updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
//        return transactionService.updateTransaction(id, transaction);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public List<TransactionDTO> getUserTransactions(@PathVariable Long userId) {
        return transactionService.getByUserId(userId)
                .stream()
                .map(transactionService::toDTO)
                .toList();
    }

    @PostMapping("/groups/{groupId}")
    public TransactionDTO createTransaction(
            @PathVariable Long userId,
            @PathVariable Long groupId,
            @RequestBody Transaction transaction) {
        return transactionService.toDTO(transactionService.saveTransaction(userId, groupId, transaction));
    }
}
