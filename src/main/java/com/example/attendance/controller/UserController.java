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

import java.security.Principal;
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
    public String showUserReport(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login"; // Not logged in
        }

        User user = userService.findByUsername(username);
        if (user == null) {
            model.addAttribute("error", "User not found");
            return "login";
        }

        Student student = studentService.getStudentByUser(user);
        if (student == null) {
            model.addAttribute("error", "Student not linked to user");
            return "user/home"; // or some fallback page
        }
//        System.out.println("_______________"+student.getName());
        List<Attendance> attendanceList = attendanceService.getAttendanceByStudent(student.getId());

        model.addAttribute("student", student);
        model.addAttribute("attendanceList", attendanceList);

        return "user/report"; // ➤ Create a `user/report.html` page to show this info
    }



    @GetMapping("/home")
    public String userHome(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        User user = userService.findByUsername(username);
        if (user == null) {
            model.addAttribute("error", "User not found");
            return "redirect:/login";
        }

        Student student = studentService.getStudentByUser(user);
        if (student == null) {
            model.addAttribute("error", "Student not linked to user");
            return "redirect:/login";
        }

        model.addAttribute("username", username);
        model.addAttribute("student", student); // ✅ Add this to Model

        return "user/home";
    }





}
