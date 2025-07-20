package com.example.attendance;

import com.example.attendance.entity.User;
import com.example.attendance.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AttendanceSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(AttendanceSystemApplication.class, args);
    }
    @Bean
    public CommandLineRunner initUserAndAdmin(UserRepository userRepository) {
        return args -> {
            // Admin user
            if (userRepository.findByUsername("admin") == null) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin123"); // In production, hash this!
                admin.setRole("ADMIN");
                userRepository.save(admin);
                System.out.println("Admin user inserted");
            } else {
                System.out.println("Admin already exists");
            }

            // Default user
            if (userRepository.findByUsername("user") == null) {
                User user = new User();
                user.setUsername("user");
                user.setPassword("user123"); // In production, hash this!
                user.setRole("USER");
                userRepository.save(user);
                System.out.println("User inserted");
            } else {
                System.out.println("User already exists");
            }
        };
    }

}