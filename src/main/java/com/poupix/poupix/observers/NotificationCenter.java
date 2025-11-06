package com.poupix.poupix.observers;

import com.poupix.poupix.entity.Group;
import com.poupix.poupix.entity.Transaction;
import com.poupix.poupix.entity.User;

import java.util.ArrayList;
import java.util.List;

public class NotificationCenter {

    private static NotificationCenter instance;
    private List<TransactionObserver> transactionObservers = new ArrayList<>();
    private List<UserObserver> userObservers = new ArrayList<>();
    private List<GroupObserver> groupObservers = new ArrayList<>();

    private NotificationCenter() {
        registerDefaultObservers();
    }

    private void registerDefaultObservers() {
        transactionObservers.add(new ConsoleTransactionObserver());
        userObservers.add(new ConsoleUserObserver());
        groupObservers.add(new ConsoleGroupObserver());
    }

    public static synchronized NotificationCenter getInstance() {
        if (instance == null) {
            instance = new NotificationCenter();
        }
        return instance;
    }

    public void addTransactionObserver(TransactionObserver observer) {
        transactionObservers.add(observer);
    }

    public void notifyTransactionCreated(Transaction transaction) {
        transactionObservers.forEach(o -> o.onTransactionCreated(transaction));
    }

    public void notifyTransactionDeleted(Transaction transaction) {
        transactionObservers.forEach(o -> o.onTransactionDeleted(transaction));
    }

    public void addUserObserver(UserObserver observer) {
        userObservers.add(observer);
    }

    public void notifyUserCreated(User user) {
        userObservers.forEach(o -> o.onUserCreated(user));
    }

    public void addGroupObserver(GroupObserver observer) {
        groupObservers.add(observer);
    }

    public void notifyGroupCreated(Group group) {
        groupObservers.forEach(o -> o.onGroupCreated(group));
    }

    public void notifyGroupDeleted(Group group) {
        groupObservers.forEach(o -> o.onGroupDeleted(group));
    }
}
