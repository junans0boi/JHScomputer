package com.example.jhscomputer.router.controller;

import com.example.jhscomputer.users.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterController {

    private final UserManagementService userService;

    @Autowired
    public RouterController(UserManagementService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "user/users/login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "user/users/register";
    }

    @GetMapping("/asrequest")
    public String helpcenter() {
        return "user/asrequest/asrequest-form";  // 수정: 사용자 asrequest 템플릿 경로
    }

    @GetMapping("/assemblyorder")
    public String order() {
        return "user/assemblyorder/assemblyorder-form";
    }


    @GetMapping("/announcements")
    public String postList() {
        return "user/announcement/announcement-list";
    }

    @GetMapping("/youtubeAPI")
    public String youtubeAPI() {
        return "youtubeAPI";
    }

    @GetMapping("/user/mypage")
    public String showProfilePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        var currentUser = userService.findByEmail(userEmail);
        if (currentUser == null) {
            return "redirect:/login?error=user_not_found";
        }
        model.addAttribute("user", currentUser);
        return "user/profile/mypage-layout";
    }
}