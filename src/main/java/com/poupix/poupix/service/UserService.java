package com.poupix.poupix.service;

import com.poupix.poupix.dto.UserDTO;
import com.poupix.poupix.dto.GroupDTO;
import com.poupix.poupix.entity.User;
import com.poupix.poupix.observers.ConsoleTransactionObserver;
import com.poupix.poupix.observers.ConsoleUserObserver;
import com.poupix.poupix.observers.UserObserver;
import com.poupix.poupix.repository.GroupRepository;
import com.poupix.poupix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    private final List<UserObserver> observers = new ArrayList<>();

    public UserService() {
        observers.add(new ConsoleUserObserver());
    }

    public Optional<UserDTO> getUserById(Long id){
        return userRepository.findById(id).map(this::toDTO);
    }

    public User createUser(User user){
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        // Notifica todos os observers
        for (UserObserver observer : observers) {
            observer.onUserCreated(savedUser);
        }

        return savedUser;
    }

    public List<GroupDTO> getUserGroups(Long userId) {
        // supondo que você tenha um relacionamento User -> Groups
        return groupRepository.findByUsersId(userId)
                .stream()
                .map(GroupDTO::fromEntity)
                .toList();
    }


    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public UserDTO updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());

                    // Atualiza senha SOMENTE se vier algo no body
                    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                        user.setPassword(updatedUser.getPassword());
                    }

                    User savedUser = userRepository.save(user);
                    return toDTO(savedUser);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    //Mapper
    public UserDTO toDTO(User user) {
        List<GroupDTO> groups = user.getGroups().stream()
                .map(group -> new GroupDTO(
                        group.getId(),
                        group.getUuid(), // UUID agora
                        group.getName(),
                        null,            // lista de usuários pode ser null se não quiser carregar
                        group.getDescription()
                ))
                .toList();

        return new UserDTO(user.getId(), user.getName(), user.getEmail(), groups);
    }
}
