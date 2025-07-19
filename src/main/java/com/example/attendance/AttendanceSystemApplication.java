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
    public CommandLineRunner initAdminUser(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin") == null) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin123"); // Plain text for now; can hash if needed
                admin.setRole("ADMIN");
                userRepository.save(admin);
                System.out.println("Admin user inserted");
            } else {
                System.out.println("Admin already exists");
            }
        };
    }
}