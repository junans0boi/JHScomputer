package com.example.jhscomputer.users.repository;

import com.example.jhscomputer.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// User 엔티티에 대한 데이터 접근을 수행하는 Repository 인터페이스입니다.
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 특정 이메일을 가진 사용자가 이미 존재하는지 여부 체크
    boolean existsByEmail(String email);

    // 특정 전화번호를 가진 사용자가 이미 존재하는지 여부 체크
    boolean existsByPhoneNum(String phoneNum);

    // ---------------------------------------
    // 이메일로 회원 정보를 조회
    // email 조회할 이메일
    // @return 해당 이메일을 가진 User 엔티티 또는 null
    // ---------------------------------------
    User findByEmail(String email);
}