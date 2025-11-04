package com.poupix.poupix.observers;


import com.poupix.poupix.entity.Group;

public class ConsoleGroupObserver implements GroupObserver {
    @Override
    public void onGroupCreated(Group group) {
        String groupName = group.getName();
        System.out.println(
                "[Observer] New Group created: " +
                        "ID: " + group.getId() +
                        " | Name: " + groupName +
                        "\n--------------------------------------------"
        );
    }

    @Override
    public void onGroupDeleted(Group group) {
        String groupName = group.getName();
        System.out.println(
                "[Observer] Group deleted: " +
                        "ID: " + group.getId() +
                        " | Name: " + groupName +
                        "\n--------------------------------------------"
        );

    }
}
