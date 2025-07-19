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

    public void saveStudent(Student student) {
        studentRepository.save(student);
    }
    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }
    public Student getStudentByUser(User user) {
        return studentRepository.findByUser(user);
    }
}
