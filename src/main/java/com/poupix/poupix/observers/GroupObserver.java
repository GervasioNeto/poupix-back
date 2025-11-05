package com.poupix.poupix.observers;

import com.poupix.poupix.entity.Group;

public interface GroupObserver {

    void onGroupCreated(Group group);

    void onGroupDeleted(Group group);
}
