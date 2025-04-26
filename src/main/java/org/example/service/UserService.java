package org.example.service;

import org.example.entity.User;
import org.example.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
    public void register(String username, String password, String role) {
        User user = new User(username, password, role);
        userRepository.save(user);
    }
}
