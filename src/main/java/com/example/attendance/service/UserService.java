// UserService.java
package com.example.attendance.service;

import com.example.attendance.entity.Student;
import com.example.attendance.entity.User;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;

    public User authenticate(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
    public void createUser(String username, String password, String role) {
        if (userRepository.findByUsername(username) == null) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password); // Consider encoding password in real apps
            user.setRole(role.toUpperCase());
            userRepository.save(user);
        }
    }

    // Register user from a registration form
    public void registerUser(String username, String password, String role) {
        createUser(username, password, role); // Reuse logic
    }

    // Get user by username (for login)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}

