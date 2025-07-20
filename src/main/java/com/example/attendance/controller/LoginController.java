package com.example.attendance.controller;

import com.example.attendance.entity.User;
import com.example.attendance.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    // Show login page
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        User user = userService.authenticate(username, password);

        if (user == null) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }

        // ✅ Save username in session
        session.setAttribute("username", user.getUsername());

        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/admin/dashboard";
        } else if ("USER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/user/viewreport";
        } else {
            model.addAttribute("error", "Invalid role");
            return "login";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // ✅ clear session
        return "redirect:/login";
    }
}
