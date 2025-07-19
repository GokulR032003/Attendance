// UserController.java
package com.example.attendance.controller;

import com.example.attendance.entity.Attendance;
import com.example.attendance.entity.Student;
import com.example.attendance.entity.User;
import com.example.attendance.service.AttendanceService;
import com.example.attendance.service.StudentService;
import com.example.attendance.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private StudentService studentService;

    @GetMapping("/viewreport")
    public String viewReport(HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUsername");

        if (username == null) {
            return "redirect:/login";
        }

        User user = userService.findByUsername(username);
            Student student = studentService.getStudentByUser(user);
        if (student == null) {
            model.addAttribute("error", "Student not found for user.");
            return "redirect:/login";
        }

        Long studentId = user.getStudent().getId();
        List<Attendance> attendanceList = attendanceService.getAttendanceByStudent(studentId);
        model.addAttribute("attendanceList", attendanceList);

        return "user/viewreport";
    }
}
