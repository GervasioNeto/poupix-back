package com.poupix.poupix.observers;

import com.poupix.poupix.entity.User;

public class ConsoleUserObserver implements UserObserver {

    @Override
    public void onUserCreated(User user) {
        String userName = user.getName() != null ? user.getName() : "Nome desconhecido";
        String email = user.getEmail() != null ? user.getEmail() : "Email não informado";

        System.out.println(
                "[Observer] New User created: " +
                        "ID: " + user.getId() +
                        " | Nome: " + userName +
                        " | Email: " + email +
                        "\n--------------------------------------------"
        );
    }
}
