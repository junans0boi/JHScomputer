package com.example.jhscomputer.asrequest.repository;

import com.example.jhscomputer.asrequest.entity.ASRequest;
import com.example.jhscomputer.asrequest.enumpackage.ASRequestStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ASRequestRepository extends JpaRepository<ASRequest, Long> {
    
    // 사용자별 요청 목록 조회
    List<ASRequest> findByUserUserId(Long userId);
    
    // 특정 상태의 요청 목록 조회
    List<ASRequest> findByStatus(ASRequestStatus status);
    
    // 기타 필요한 커스텀 메서드 추가 가능
}