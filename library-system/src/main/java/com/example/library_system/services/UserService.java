package com.example.library_system.services;

import com.example.library_system.controllers.dts.UserDTO;
import com.example.library_system.controllers.mappers.UserMapper;
import com.example.library_system.entities.User;
import com.example.library_system.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,  UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void save(UserDTO dto) {
        userRepository.save(userMapper.toEntity(dto));
    }

    public Optional<UserDTO> getUserById(UUID id) {
        var user = userRepository.findById(id);
        return user.map(userMapper::toDTO);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(
                userMapper::toDTO).collect(Collectors.toList());
    }

    public UserDTO update(UUID id, UserDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());

        var updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
