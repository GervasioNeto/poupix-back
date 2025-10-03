package com.poupix.poupix.service;

import com.poupix.poupix.dto.TransactionDTO;
import com.poupix.poupix.entity.Group;
import com.poupix.poupix.entity.Transaction;
import com.poupix.poupix.entity.User;
import com.poupix.poupix.repository.GroupRepository;
import com.poupix.poupix.repository.TransactionRepository;
import com.poupix.poupix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

//    public TransactionService(TransactionRepository transactionRepository) {
//        this.transactionRepository = transactionRepository;
//    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public List<TransactionDTO> getUserTransactions(Long userId) {
        return transactionRepository.findByUserId(userId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public Transaction saveTransaction(Long userId, Long groupId, Transaction transaction) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        transaction.setUser(user);
        transaction.setGroup(group);

        return transactionRepository.save(transaction);
    }

    public Transaction createTransaction(TransactionDTO dto, Long groupId) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));

        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setType(dto.getType());
        transaction.setCategory(dto.getCategory());
        transaction.setDescription(dto.getDescription());
        transaction.setDate(dto.getDate());
        transaction.setUser(user);
        transaction.setGroup(group);

        return transactionRepository.save(transaction);
    }

    public Transaction createTransaction(Long groupId, TransactionDTO dto) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));

        Transaction transaction = new Transaction();
        transaction.setDescription(dto.getDescription());
        transaction.setAmount(dto.getAmount());
        transaction.setGroup(group);

        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    @Transactional
    public Transaction updateTransactionByUserId(Long userId, Transaction newTransaction) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction atualTransaction = transactionRepository.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("Trasaction not found for userId :" + userId));

        atualTransaction.setAmount(newTransaction.getAmount());
        atualTransaction.setType(newTransaction.getType());
        atualTransaction.setCategory(newTransaction.getCategory());
        atualTransaction.setDescription(newTransaction.getDescription());
        atualTransaction.setDate(newTransaction.getDate());
        atualTransaction.setCreatedAt(newTransaction.getCreatedAt());

        atualTransaction.setUser(user);

        return transactionRepository.save(atualTransaction);
    }

    @Transactional
    public Transaction updateTransaction(Long transactionId, Transaction newTransaction) {
        Transaction atualTransaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + transactionId));

        // Atualiza campos
        atualTransaction.setAmount(newTransaction.getAmount());
        atualTransaction.setType(newTransaction.getType());
        atualTransaction.setCategory(newTransaction.getCategory());
        atualTransaction.setDescription(newTransaction.getDescription());
        atualTransaction.setDate(newTransaction.getDate());
        atualTransaction.setCreatedAt(newTransaction.getCreatedAt());

        // Se quiser atualizar User e Group:
        if (newTransaction.getUser() != null) {
            atualTransaction.setUser(newTransaction.getUser());
        }
        if (newTransaction.getGroup() != null) {
            atualTransaction.setGroup(newTransaction.getGroup());
        }

        return transactionRepository.save(atualTransaction);
    }

//    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
//        return transactionRepository.findById(id)
//                .map(transaction -> {
//                    transaction.setAmount(updatedTransaction.getAmount());
//                    transaction.setType(updatedTransaction.getType());
//                    transaction.setCategory(updatedTransaction.getCategory());
//                    transaction.setDescription(updatedTransaction.getDescription());
//                    transaction.setDate(updatedTransaction.getDate());
//
//                    Transaction saved = transactionRepository.save(transaction);
//                    return transactionRepository.save(transaction);
//                })
//                .orElseThrow(() -> new RuntimeException("Transaction não encontrada"));
//    }

    public List<Transaction> getByUserId(Long userId) {
        return transactionRepository.findAllByUserId(userId);
    }

    public TransactionDTO toDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setUserId(transaction.getUser() != null ? transaction.getUser().getId() : null);
        dto.setGroupId(transaction.getGroup() != null ? transaction.getGroup().getId() : null);
        dto.setAmount(transaction.getAmount());
        dto.setType(transaction.getType());
        dto.setCategory(transaction.getCategory());
        dto.setDescription(transaction.getDescription());
        dto.setDate(transaction.getDate());
        dto.setCreatedAt(transaction.getCreatedAt());
        return dto;
    }
}
