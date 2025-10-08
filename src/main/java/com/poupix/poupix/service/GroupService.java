package com.poupix.poupix.service;

import com.poupix.poupix.dto.GroupDTO;
import com.poupix.poupix.dto.UserDTO;
import com.poupix.poupix.entity.Group;
import com.poupix.poupix.entity.Transaction;
import com.poupix.poupix.entity.User;
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

    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }

    // Lista os usuários de um grupo
    public List<User> getGroupUsers(Long groupId) {
        Group group = getGroupById(groupId);
        return new ArrayList<>(group.getUsers()); // supondo que Group tenha getUsers()
    }

    // Lista as transações de um grupo
    public List<Transaction> getGroupTransactions(Long groupId) {
        Group group = getGroupById(groupId);
        return new ArrayList<>(group.getTransactions()); // supondo que Group tenha getTransactions()
    }

    public GroupDTO toDTO(Group group) {
        // mapeia os usuários do grupo para UserDTO (sem senha, sem transações)
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
        // Salva o grupo
        Group savedGroup = groupRepository.save(group);

        // Busca o usuário criador
        User creator = userRepository.findById(creatorUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Adiciona o usuário ao grupo
        savedGroup.getUsers().add(creator);

        // Salva novamente o grupo com o usuário adicionado
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

    public Group updateGroup(Long groupId, Group groupData) {
        Group existingGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));

        existingGroup.setName(groupData.getName());
        existingGroup.setDescription(groupData.getDescription());
        // atualize outros campos se necessário

        return groupRepository.save(existingGroup);
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }
}
