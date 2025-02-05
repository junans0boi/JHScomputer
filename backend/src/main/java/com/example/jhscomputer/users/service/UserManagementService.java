package com.example.jhscomputer.users.service;

import com.example.jhscomputer.users.entity.User;
import com.example.jhscomputer.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserManagementService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ---------------------------------------
    // [회원] 전화번호를 '010-XXXX-XXXX' 형식으로 변환합니다.
    // ---------------------------------------
    private String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }

        // 모든 비숫자 문자 제거
        String digits = phoneNumber.replaceAll("\\D", "");

        // '010'으로 시작하고 총 11자리인지 확인
        if (digits.startsWith("010") && digits.length() == 11) {
            // 하이픈 삽입: 010-XXXX-XXXX
            return digits.replaceFirst("(010)(\\d{4})(\\d{4})", "$1-$2-$3");
        } else {
            // 유효하지 않은 형식인 경우 처리 (예: 예외를 던지거나 기본 형식 유지)
            // 여기서는 유효하지 않은 형식일 경우 null 반환
            return null;
        }
    }

    // ---------------------------------------
    // [회원] 사용자 이름 찾기
    // ---------------------------------------
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("이메일로 사용자를 찾을 수 없습니다: " + email);
        }
        return new UserSecurityService(user);
    }

    // ---------------------------------------
    // [회원] 이메일 찾기
    // ---------------------------------------
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // ---------------------------------------
    // [회원] 회원 저장
    // ---------------------------------------
    public User save(User user) {
        return userRepository.save(user);
    }

    // ---------------------------------------
    // [회원] 회원가입 로직
    // ---------------------------------------
    public String registerUser(User user) {
        // 이메일 중복 확인
        if (userRepository.existsByEmail(user.getEmail())) {
            return "이미 존재하는 이메일입니다.";
        }
        // 전화번호 중복 확인
        if (userRepository.existsByPhoneNum(user.getPhoneNum())) {
            return "이미 존재하는 전화번호입니다.";
        }

        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 전화번호 형식 변환
        String formattedPhone = formatPhoneNumber(user.getPhoneNum());
        if (formattedPhone == null) {
            return "유효하지 않은 전화번호 형식입니다.";
        }
        user.setPhoneNum(formattedPhone);

        // 기본 사용자 역할 부여
        user.setRoles(Set.of("USER"));

        // DB 저장
        userRepository.save(user);
        return "회원가입이 성공적으로 완료되었습니다.";
    }
}