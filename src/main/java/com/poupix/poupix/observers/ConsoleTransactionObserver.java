package com.poupix.poupix.observers;

import com.poupix.poupix.entity.Transaction;

public class ConsoleTransactionObserver implements TransactionObserver {

    @Override
    public void onTransactionCreated(Transaction transaction) {
        String userName = transaction.getUser() != null ? transaction.getUser().getName() : "Desconhecido";
        String groupName = transaction.getGroup() != null ? transaction.getGroup().getName() : "Desconhecido";

        System.out.println(
                "\n[Observer] ✅ Nova transação registrada!" +
                        "\nUsuário: " + userName +
                        "\nGrupo: " + groupName +
                        "\nDescrição: " + transaction.getDescription() +
                        "\nValor: R$ " + transaction.getAmount() +
                        "\nTipo: " + transaction.getType() +
                        "\nCategoria: " + transaction.getCategory() +
                        "\nCriada em: " + transaction.getCreatedAt() +
                        "\n--------------------------------------------"
        );
    }

    @Override
    public void onTransactionDeleted(Transaction transaction) {
        String userName = transaction.getUser() != null ? transaction.getUser().getName() : "Desconhecido";
        String groupName = transaction.getGroup() != null ? transaction.getGroup().getName() : "Desconhecido";

        System.out.println(
                "\n[Observer] ❌ Transação deletada!" +
                        "\nUsuário: " + userName +
                        "\nGrupo: " + groupName +
                        "\nDescrição: " + transaction.getDescription() +
                        "\nValor: R$ " + transaction.getAmount() +
                        "\nTipo: " + transaction.getType() +
                        "\nCategoria: " + transaction.getCategory() +
                        "\nCriada em: " + transaction.getCreatedAt() +
                        "\n--------------------------------------------"
        );
    }

}

