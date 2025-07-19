package com.example.attendance.config;

import com.example.attendance.entity.User;
import com.example.attendance.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

public class UserInitConfig {
    @Bean
    public CommandLineRunner initDefaultAdmin(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin") == null) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin123"); // You can hash this if needed
                admin.setRole("ADMIN");
                userRepository.save(admin);
                System.out.println("Default admin user created.");
            } else {
                System.out.println("Admin user already exists.");
            }
        };
    }

}
