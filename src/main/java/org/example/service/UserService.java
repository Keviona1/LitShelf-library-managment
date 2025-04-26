package org.example.service;

import org.example.entity.User;
import org.example.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
