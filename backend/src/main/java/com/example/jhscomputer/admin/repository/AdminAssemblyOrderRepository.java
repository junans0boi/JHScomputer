package com.example.jhscomputer.admin.repository;

import com.example.jhscomputer.assemblyorder.entity.AssemblyOrder;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminAssemblyOrderRepository extends JpaRepository<AssemblyOrder, Long> {
    // ---------------------------------------
    // 특정 사용자 ID로 주문 목록을 조회
    // @param userId 사용자 ID
    // @return 주문 목록
    // ---------------------------------------
    @Query("SELECT ao FROM AssemblyOrder ao WHERE ao.user.userId = :userId")
    List<AssemblyOrder> findByUserId(@Param("userId") Long userId);
}