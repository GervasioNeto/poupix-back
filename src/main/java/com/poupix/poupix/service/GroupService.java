package com.poupix.poupix.service;

import com.poupix.poupix.dto.GroupDTO;
import com.poupix.poupix.dto.UserDTO;
import com.poupix.poupix.entity.Group;
import com.poupix.poupix.entity.Transaction;
import com.poupix.poupix.entity.User;
import com.poupix.poupix.observers.ConsoleGroupObserver;
import com.poupix.poupix.observers.GroupObserver;
import com.poupix.poupix.observers.NotificationCenter;
import com.poupix.poupix.observers.TransactionObserver;
import com.poupix.poupix.repository.GroupRepository;
import com.poupix.poupix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserRepository userRepository;

    private final NotificationCenter notificationCenter = NotificationCenter.getInstance();

    public GroupService() {
    }

    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }

    public List<User> getGroupUsers(Long groupId) {
        Group group = getGroupById(groupId);
        return new ArrayList<>(group.getUsers()); // supondo que Group tenha getUsers()
    }

    public List<Transaction> getGroupTransactions(Long groupId) {
        Group group = getGroupById(groupId);
        return new ArrayList<>(group.getTransactions()); // supondo que Group tenha getTransactions()
    }

    public GroupDTO toDTO(Group group) {
        List<UserDTO> users = group.getUsers().stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail()))
                .toList();

        return new GroupDTO(
                group.getId(),
                group.getUuid(),
                group.getName(),
                users,
                group.getDescription()
        );
    }

    public Group createGroup(Group group, Long creatorUserId) {
        Group savedGroup = groupRepository.save(group);

        User creator = userRepository.findById(creatorUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        savedGroup.getUsers().add(creator);

        notificationCenter.notifyGroupCreated(savedGroup);

        return groupRepository.save(savedGroup);
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group addUserToGroup(Long groupId, Long userId) {
        Group group = getGroupById(groupId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        group.getUsers().add(user);
        return groupRepository.save(group);
    }

    public Group addUserToGroupByEmail(Long groupId, String email) {
        Group group = getGroupById(groupId);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (group.getUsers().contains(user)) {
            throw new RuntimeException("Usuário já está no grupo");
        }

        return addUserToGroup(groupId, user.getId());
    }

    public Group updateGroup(Long groupId, Group groupData) {
        Group existingGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));

        existingGroup.setName(groupData.getName());
        existingGroup.setDescription(groupData.getDescription());

        return groupRepository.save(existingGroup);
    }

    public Group removeUserFromGroup(Long groupId, Long userId) {
        Group group = getGroupById(groupId);

        if (group.getUsers().size() <= 1) {
            throw new RuntimeException("Não é possível remover o último integrante do grupo. Exclua o grupo inteiro.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        group.getUsers().remove(user);

        return groupRepository.save(group);
    }

    public void deleteGroup(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        notificationCenter.notifyGroupDeleted(group);

        groupRepository.deleteById(id);
    }
}
