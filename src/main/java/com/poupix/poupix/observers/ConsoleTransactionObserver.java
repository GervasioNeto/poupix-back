package com.poupix.poupix.observers;

import com.poupix.poupix.entity.Transaction;

public class ConsoleTransactionObserver implements TransactionObserver {

    @Override
    public void onTransactionCreated(Transaction transaction) {
        String userName = transaction.getUser() != null ? transaction.getUser().getName() : "Desconhecido";
        String groupName = transaction.getGroup() != null ? transaction.getGroup().getName() : "Desconhecido";

        System.out.println(
                "[Observer] New Transaction Created: " +
                        "ID:" + transaction.getId() +
                        " | Group: " + groupName +
                        " | Created at: " + transaction.getCreatedAt() +
                        "\n--------------------------------------------"
        );
    }

    @Override
    public void onTransactionDeleted(Transaction transaction) {
        String userName = transaction.getUser() != null ? transaction.getUser().getName() : "Desconhecido";
        String groupName = transaction.getGroup() != null ? transaction.getGroup().getName() : "Desconhecido";

        System.out.println(
                "[Observer] Transaction Deleted: " +
                        "ID:" + transaction.getId() +
                        " | Group: " + groupName +
                        "\n--------------------------------------------"
        );
    }
}

