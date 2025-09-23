package com.poupix.poupix.service;

import com.poupix.poupix.dto.TransactionDTO;
import com.poupix.poupix.entity.Transaction;
import com.poupix.poupix.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public Transaction createTransaction(Transaction transaction) {
        Transaction saved = transactionRepository.save(transaction);
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        return transactionRepository.findById(id)
                .map(transaction -> {
                    transaction.setAmount(updatedTransaction.getAmount());
                    transaction.setType(updatedTransaction.getType());
                    transaction.setCategory(updatedTransaction.getCategory());
                    transaction.setDescription(updatedTransaction.getDescription());
                    transaction.setDate(updatedTransaction.getDate());

                    Transaction saved = transactionRepository.save(transaction);
                    return transactionRepository.save(transaction);
                })
                .orElseThrow(() -> new RuntimeException("Transaction não encontrada"));
    }

//    private TransactionDTO toDTO(Transaction transaction) {
//        return new TransactionDTO(
//                transaction.getId(),
//                transaction.getAmount(),
//                transaction.getType(),
//                transaction.getCategory(),
//                transaction.getDescription(),
//                transaction.getDate(),
//                transaction.getCreatedAt()
//        );
//    }
}
