package com.example.jhscomputer.users.controller;

import com.example.jhscomputer.users.entity.User;
import com.example.jhscomputer.users.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 인증/인가와 관련된 요청(회원가입, 로그인 등)을 처리하는 컨트롤러입니다.
 */
@Controller
public class UserController {

    @Autowired
    private UserManagementService userService;

    /**
     * 회원가입(POST)을 처리하는 메서드입니다.
     * 폼에서 전송된 데이터(email, password, ... )를 받아 새로운 회원을 등록합니다.
     *
     * @param email              사용자가 입력한 이메일
     * @param password           사용자가 입력한 비밀번호
     * @param passwordConfirm    비밀번호 확인
     * @param username           사용자 이름
     * @param phoneNum           전화번호
     * @param addressBase        기본 주소
     * @param addressDetail      상세 주소
     * @param redirectAttributes 회원가입 실패 시 에러 메시지를 전달하거나, 성공 시 성공 플래그를 전달
     * @return 회원가입 성공 시 로그인 페이지로 리다이렉트, 실패 시 회원가입 페이지로 리다이렉트
     */
    @PostMapping("/api/register")
    public String registerUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("passwordConfirm") String passwordConfirm,
            @RequestParam("name") String username,
            @RequestParam("phoneNum") String phoneNum,
            @RequestParam("sample6_address") String addressBase,
            @RequestParam("sample6_detailAddress") String addressDetail,
            RedirectAttributes redirectAttributes) {
        // 비밀번호 확인
        if (!password.equals(passwordConfirm)) {
            redirectAttributes.addFlashAttribute("registrationError", "비밀번호가 일치하지 않습니다.");
            return "redirect:/register";
        }

        // User 엔티티 생성 & 데이터 매핑
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setUsername(username);
        newUser.setPhoneNum(phoneNum);
        newUser.setAddress(addressBase + " " + addressDetail);

        // 회원가입 로직 수행
        String result = userService.registerUser(newUser);

        if ("회원가입이 성공적으로 완료되었습니다.".equals(result)) {
            redirectAttributes.addFlashAttribute("registrationSuccess", true);
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("registrationError", result);
            return "redirect:/register";
        }
    }

}