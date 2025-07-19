// AttendanceRepository.java
package com.example.attendance.repository;
import com.example.attendance.entity.Attendance;
import com.example.attendance.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    int countByStudentAndPresent(Student student, boolean present);
    List<Attendance> findByStudent(Student student);
    Optional<Attendance> findByStudentAndDate(Student student, LocalDate date);
    List<Attendance> findByStudentId(Long studentId);
}
