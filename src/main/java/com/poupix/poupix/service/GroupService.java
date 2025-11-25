package com.poupix.poupix.service;

import com.poupix.poupix.dto.GroupDTO;
import com.poupix.poupix.dto.GroupMemberDTO;
import com.poupix.poupix.entity.Group;
import com.poupix.poupix.entity.GroupMember;
import com.poupix.poupix.entity.Transaction;
import com.poupix.poupix.entity.User;
import com.poupix.poupix.enums.GroupRole;
import com.poupix.poupix.observers.NotificationCenter;
import com.poupix.poupix.repository.GroupRepository;
import com.poupix.poupix.repository.GroupMemberRepository;
import com.poupix.poupix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private GroupMemberRepository groupUserRepository;

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

        return group.getGroupMembers()
                .stream()
                .map(GroupMember::getUser)
                .toList();
    }

    public List<GroupMember> getGroupMembers(Long groupId) {
        Group group = getGroupById(groupId);
        return groupUserRepository.findByGroup(group);
    }

    public List<Transaction> getGroupTransactions(Long groupId) {
        Group group = getGroupById(groupId);
        return new ArrayList<>(group.getTransactions()); // supondo que Group tenha getTransactions()
    }

    public GroupDTO toDTO(Group group) {

        List<GroupMemberDTO> members = group.getGroupMembers().stream()
                .map(m -> new GroupMemberDTO(
                        m.getUser().getId(),
                        m.getUser().getName(),
                        m.getUser().getEmail(),
                        m.getRole().name()
                ))
                .toList();

        return new GroupDTO(
                group.getId(),
                group.getUuid(),
                group.getName(),
                members,
                group.getDescription()
        );
    }

    public Group createGroup(Group group, Long creatorUserId) {
        Group savedGroup = groupRepository.save(group);

        User creator = userRepository.findById(creatorUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Criador entra como admin
        GroupMember gu = new GroupMember();
        gu.setGroup(savedGroup);
        gu.setUser(creator);
        gu.setRole(GroupRole.ADMIN);
        groupUserRepository.save(gu);

        notificationCenter.notifyGroupCreated(savedGroup);

        return savedGroup;
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

//    public Group addUserToGroup(Long groupId, Long userId) {
//        Group group = getGroupById(groupId);
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        group.getUsers().add(user);
//        return groupRepository.save(group);
//    }

    public Group addUserToGroup(Long groupId, Long userId) {
        Group group = getGroupById(groupId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (groupUserRepository.existsByGroupAndUser(group, user)) {
            throw new RuntimeException("User already in group");
        }

        GroupMember gu = new GroupMember();
        gu.setUser(user);
        gu.setGroup(group);
        gu.setRole(GroupRole.MEMBER);

        groupUserRepository.save(gu);

        return group;
    }

    public Group addUserToGroupByEmail(Long groupId, String email) {
        Group group = getGroupById(groupId);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        boolean alreadyInGroup = group.getGroupMembers().stream()
                .anyMatch(m -> m.getUser().getId().equals(user.getId()));

        if (alreadyInGroup) {
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

//    public Group updateGroup(Long id, Group data) {
//        Group group = getGroupById(id);
//        group.setName(data.getName());
//        group.setDescription(data.getDescription());
//        return groupRepository.save(group);
//    }

//    public Group removeUserFromGroup(Long groupId, Long userId) {
//        Group group = getGroupById(groupId);
//
//        if (group.getUsers().size() <= 1) {
//            throw new RuntimeException("Não é possível remover o último integrante do grupo. Exclua o grupo inteiro.");
//        }
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
//
//        group.getUsers().remove(user);
//
//        return groupRepository.save(group);
//    }

    public Group removeUserFromGroup(Long groupId, Long userId) {
        Group group = getGroupById(groupId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        GroupMember relation = groupUserRepository.findByGroupAndUser(group, user);

        if (relation == null)
            throw new RuntimeException("User is not in group");

        // Impede remover último integrante
        if (group.getGroupMembers().size() <= 1)
            throw new RuntimeException("Cannot remove last member. Delete the group instead.");

        groupUserRepository.delete(relation);

        return group;
    }

    public void deleteGroup(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        notificationCenter.notifyGroupDeleted(group);

        groupRepository.deleteById(id);
    }

    // ======================
    // ADMIN ACTIONS
    // ======================

    public void promoteUser(Long groupId, Long userId) {
        Group group = getGroupById(groupId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        GroupMember gu = groupUserRepository.findByGroupAndUser(group, user);
        if (gu == null) throw new RuntimeException("User not in group");

        gu.setRole(GroupRole.ADMIN);
        groupUserRepository.save(gu);
    }

    public void demoteUser(Long groupId, Long userId) {
        Group group = getGroupById(groupId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        GroupMember gu = groupUserRepository.findByGroupAndUser(group, user);
        if (gu == null) throw new RuntimeException("User not in group");

        gu.setRole(GroupRole.MEMBER);
        groupUserRepository.save(gu);
    }

    public List<Group> searchGroups(String query) {
        // Verifica se é UUID válido
        if (isUUID(query)) {
            Group group = groupRepository.findByUuid(query);
            if (group != null) return List.of(group);
            return List.of(); // UUID válido, mas não encontrado
        }

        // Caso contrário, busca por nome
        return groupRepository.findByNameContainingIgnoreCase(query);
    }

    private boolean isUUID(String value) {
        try {
            UUID.fromString(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Group> searchByDateRange(LocalDateTime start, LocalDateTime end) {
        return groupRepository.findByCreatedAtBetween(start, end);
    }
}
