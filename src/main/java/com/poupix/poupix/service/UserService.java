package com.poupix.poupix.service;

import com.poupix.poupix.dto.UserDTO;
import com.poupix.poupix.entity.User;
import com.poupix.poupix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    //Mapper
    private UserDTO toDTO(User user){
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getTransactions());
    }

//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public User createUser(User user) {
//        user.setCreatedAt(LocalDateTime.now());
//        return userRepository.save(user);
//    }
//
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    public User getUserById(Long id) {
//        return userRepository.findById(id).orElse(null);
//    }
//
//    public User getUserByEmail(String email) {
//        return userRepository.findByEmail(email).orElse(null);
//    }
}
