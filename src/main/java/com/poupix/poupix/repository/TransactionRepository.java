package com.poupix.poupix.repository;

import com.poupix.poupix.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);

    Optional<Transaction> findUserById(Long userId);

    List<Transaction> findAllByUserId(Long userId);

    List<Transaction> findByGroupId(Long groupId);
}
