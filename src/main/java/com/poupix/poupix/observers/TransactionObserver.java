package com.poupix.poupix.observers;

import com.poupix.poupix.entity.Transaction;

public interface TransactionObserver {
    void onTransactionCreated(Transaction transaction);

    void onTransactionDeleted(Transaction transaction);

//    void onTransactionUpdated(Transaction transaction);
}
