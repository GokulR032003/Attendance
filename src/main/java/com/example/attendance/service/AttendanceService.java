
package com.example.attendance.service;

import com.example.attendance.entity.Attendance;
import com.example.attendance.entity.Student;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentRepository studentRepository;

    public void markAttendance(Long studentId, boolean present) {
        Attendance att = new Attendance();
        att.setDate(LocalDate.now());
        att.setPresent(present);

        Student student = studentRepository.findById(studentId).orElse(null);
        att.setStudent(student);

        attendanceRepository.save(att);
    }

    public List<Attendance> getMonthlyReport() {
        return attendanceRepository.findAll(); // optionally filter by current month
    }

    public int countByStudentAndPresent(Student student, boolean present) {
        return attendanceRepository.countByStudentAndPresent(student, present);
    }

    public List<Attendance> getAttendanceByStudent(Long studentId) {
        if (studentId == null) {
            System.out.println("Student ID is null!");
            return Collections.emptyList();
        }

        List<Attendance> attendanceList = attendanceRepository.findByStudentId(studentId);
        System.out.println("Found " + attendanceList.size() + " attendance records for student ID: " + studentId);
        System.out.println("_________"+attendanceList);
        return attendanceList;
    }

    public void saveAttendance(Attendance attendance) {
        // Optional: Check if student and date already has a record
        Optional<Attendance> existing = attendanceRepository
                .findByStudentAndDate(attendance.getStudent(), attendance.getDate());

        if (existing.isPresent()) {
            // Update existing attendance if needed
            Attendance existingAttendance = existing.get();
            existingAttendance.setPresent(attendance.isPresent());
            attendanceRepository.save(existingAttendance);
        } else {
            // Save new attendance
            attendanceRepository.save(attendance);
        }
    }

}
