// AdminController.java
package com.example.attendance.controller;

import com.example.attendance.entity.Attendance;
import com.example.attendance.entity.Student;
import com.example.attendance.entity.User;
import com.example.attendance.repository.StudentRepository;
import com.example.attendance.service.AttendanceService;
import com.example.attendance.service.StudentService;
import com.example.attendance.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private UserService userService;
    @Autowired
    private StudentRepository studentRepository;

    // Dashboard
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "admin/dashboard";
    }

    // Add Student form (matches /admin/add-student)
    @GetMapping("/add-student")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "admin/add-student";
    }

    // Save new student (POST)
    @PostMapping("/add-student")
    public String addStudent(@RequestParam String username, @RequestParam String name,
                             @RequestParam String department, Model model) {

        User user = userService.findByUsername(username);
        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "admin/add-student";
        }

        if (studentService.getStudentByUser(user) != null) {
            model.addAttribute("error", "Student already exists for this user.");
            return "admin/add-student";
        }

        studentService.saveNewStudent(user, name, department);
        return "redirect:/admin/students";
    }






    // Manage Students page (matches /admin/student)
    @GetMapping("/student")
    public String manageStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        System.out.println("Students fetched: " + students.size());
        model.addAttribute("students", students);
        return "admin/student";
    }

    // Mark Attendance form (matches /admin/mark-attendance)
    @GetMapping("/mark-attendance")
    public String showMarkAttendanceForm(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "admin/mark-attendance";
    }

    // Submit attendance (POST)
    @PostMapping("/mark-attendance")
    public String markAttendance(@RequestParam Map<String, String> attendanceMap) {
        for (Map.Entry<String, String> entry : attendanceMap.entrySet()) {
            Long studentId = Long.parseLong(entry.getKey());
            boolean isPresent = entry.getValue().equalsIgnoreCase("present");

            Student student = studentService.getStudentById(studentId);
            if (student != null) {
                Attendance attendance = new Attendance();
                attendance.setStudent(student);
                attendance.setDate(LocalDate.now());
                attendance.setPresent(isPresent);
                attendanceService.saveAttendance(attendance);
            }
        }

        return "redirect:/admin/mark-attendance";
    }




    // View Reports (matches /admin/report)
    @GetMapping("/report")
    public String showReport(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);

        // Optional: Map of studentId → List<Attendance>
        Map<Long, List<Attendance>> attendanceMap = new HashMap<>();
        for (Student student : students) {
            List<Attendance> records = attendanceService.getAttendanceByStudent(student.getId());
            attendanceMap.put(student.getId(), records);
        }
        model.addAttribute("attendanceMap", attendanceMap);

        return "admin/report";
    }

    // Add User form (matches /admin/add-user)
    @GetMapping("/add-user")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/add-user";
    }

    // Save new user (POST)
    @PostMapping("/add-user")
    public String addUser(@ModelAttribute User user) {
        userService.createUser(user.getUsername(), user.getPassword(), user.getRole());
        return "redirect:/admin/dashboard";
    }
}
