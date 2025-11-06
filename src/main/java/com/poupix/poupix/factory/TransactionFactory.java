package com.poupix.poupix.factory;

import com.poupix.poupix.dto.TransactionDTO;
import com.poupix.poupix.entity.Group;
import com.poupix.poupix.entity.Transaction;
import com.poupix.poupix.entity.User;

public class TransactionFactory {

    public static Transaction create(TransactionDTO dto, User user, Group group) {
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setType(dto.getType());
        transaction.setCategory(dto.getCategory());
        transaction.setDescription(dto.getDescription());
        transaction.setDate(dto.getDate());
        transaction.setUser(user);
        transaction.setGroup(group);

        return transaction;
    }
}
