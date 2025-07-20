// StudentService.java
package com.example.attendance.service;

import com.example.attendance.entity.Student;
import com.example.attendance.entity.User;
import com.example.attendance.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student saveNewStudent(User user, String name, String department) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User must not be null and must have an ID.");
        }

        Student student = new Student();
        student.setId(user.getId());         // ID must match user ID due to @MapsId
        student.setUser(user);               // Set linked user
        student.setName(name);               // Set name
        student.setDepartment(department);   // Set department

        return studentRepository.save(student); // Save and return the persisted student
    }

    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }
    public Student getStudentByUser(User user) {

        return studentRepository.findByUser(user);

    }
}
