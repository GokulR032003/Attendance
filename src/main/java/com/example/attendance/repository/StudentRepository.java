
package com.example.attendance.repository;
import com.example.attendance.entity.Student;
import com.example.attendance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUser(User user);
}
