package com.poupix.poupix.observers;

import com.poupix.poupix.entity.Transaction;

public class ConsoleTransactionObserver implements TransactionObserver {

    @Override
    public void onTransactionCreated(Transaction transaction) {
        String userName = transaction.getUser() != null ? transaction.getUser().getName() : "Desconhecido";
        String groupName = transaction.getGroup() != null ? transaction.getGroup().getName() : "Desconhecido";

        System.out.println(
                "\n[Observer] ✅ Nova transação registrada!" +
                        "\nTransaction ID:" + transaction.getId() +
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
                        "\nTransaction ID:" + transaction.getId() +
                        "\nUsuário: " + userName +
                        "\nGrupo: " + groupName +
                        "\n--------------------------------------------"
        );
    }

//    @Override
//    public void onTransactionUpdated(Transaction transaction) {
//        String userName = transaction.getUser() != null ? transaction.getUser().getName() : "Desconhecido";
//        String groupName = transaction.getGroup() != null ? transaction.getGroup().getName() : "Desconhecido";
//
//        System.out.println(
//                "[Observer] ✏️ Transação atualizada! " +
//                        "ID: " + transaction.getId() +
//                        " | Usuário: " + userName +
//                        " | Grupo: " + groupName +
//                        " | Valor: R$ " + transaction.getAmount() +
//                        " | Tipo: " + transaction.getType()
//        );
//    }
}

