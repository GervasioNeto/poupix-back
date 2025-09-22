package com.poupix.poupix.service;

import com.poupix.poupix.dto.TransactionDTO;
import com.poupix.poupix.dto.UserDTO;
import com.poupix.poupix.entity.Transaction;
import com.poupix.poupix.entity.User;
import com.poupix.poupix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<UserDTO> getUserById(Long id){
        return userRepository.findById(id).map(this::toDTO);
    }

    public User createUser(User user){
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public UserDTO updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    user.setPassword(updatedUser.getPassword());

                    User savedUser = userRepository.save(user);
                    return toDTO(savedUser);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    public List<TransactionDTO> getTransactionsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return user.getTransactions()
                .stream()
                .map(this::toDTOTransaction)
                .collect(Collectors.toList());
    }

    // Mapper específico para transações dentro do contexto do usuário
    private TransactionDTO toDTOTransaction(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getUser().getId(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getCategory(),
                transaction.getDescription(),
                transaction.getDate(),
                transaction.getCreatedAt()
        );
    }

    //Mapper
    private UserDTO toDTO(User user){
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }
}
