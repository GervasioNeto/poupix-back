package com.poupix.poupix.service;

import com.poupix.poupix.dto.TransactionDTO;
import com.poupix.poupix.entity.Transaction;
import com.poupix.poupix.entity.User;
import com.poupix.poupix.repository.TransactionRepository;
import com.poupix.poupix.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<TransactionDTO> getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .map(this::toDTO);
    }

    public TransactionDTO createTransaction(Transaction transaction) {
        Transaction saved = transactionRepository.save(transaction);
        return toDTO(saved);
    }

    public TransactionDTO createTransaction(Long userId, Transaction transaction) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        transaction.setUser(user);
        user.addTransaction(transaction); // opcional, se você tiver addTransaction no User

        Transaction saved = transactionRepository.save(transaction);
        return toDTO(saved);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public TransactionDTO updateTransaction(Long id, Transaction updatedTransaction) {
        return transactionRepository.findById(id)
                .map(transaction -> {
                    transaction.setAmount(updatedTransaction.getAmount());
                    transaction.setType(updatedTransaction.getType());
                    transaction.setCategory(updatedTransaction.getCategory());
                    transaction.setDescription(updatedTransaction.getDescription());
                    transaction.setDate(updatedTransaction.getDate());
                    transaction.setUserId(updatedTransaction.getUserId());

                    Transaction saved = transactionRepository.save(transaction);
                    return toDTO(saved);
                })
                .orElseThrow(() -> new RuntimeException("Transaction não encontrada"));
    }

    private TransactionDTO toDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getUserId(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getCategory(),
                transaction.getDescription(),
                transaction.getDate(),
                transaction.getCreatedAt()
        );
    }
}
