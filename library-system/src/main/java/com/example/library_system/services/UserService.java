package com.example.library_system.services;

import com.example.library_system.controllers.dto.UserDTO;
import com.example.library_system.controllers.mappers.UserMapper;
import com.example.library_system.entities.User;
import com.example.library_system.exceptions.custom.EmailAlreadyExistsException;
import com.example.library_system.exceptions.custom.UserNotFoundException;
import com.example.library_system.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if(userRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyExistsException("Email is already in use: " + dto.email());
        }

        userRepository.save(userMapper.toEntity(dto));
    }

    public UserDTO getUserById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(
                userMapper::toDTO).collect(Collectors.toList());
    }

    public UserDTO update(UUID id, UserDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        if(!user.getEmail().equals(dto.email()) && userRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyExistsException("Email is already in use: " + dto.email());
        }

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
