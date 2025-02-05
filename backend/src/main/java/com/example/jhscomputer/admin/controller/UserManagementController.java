package com.example.jhscomputer.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import com.example.jhscomputer.users.entity.User;
import com.example.jhscomputer.users.repository.UserRepository;

@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserManagementController {

    @Autowired
    private UserRepository userRepository;

    // ---------------------------------------
    // [Get] // 회원 상세 정보를 JSON으로 반환 (팝업에서 AJAX로 호출)
    // ---------------------------------------
    @GetMapping("/{userId}")
    @ResponseBody
    public User getUserDetail(@PathVariable Long userId) {
        // 실제론 Service를 거쳐야 하지만 예시로 Repository 직접 호출
        return userRepository.findById(userId).orElse(null);
    }
}