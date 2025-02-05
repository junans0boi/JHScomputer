package com.example.jhscomputer.admin.service;

import com.example.jhscomputer.assemblyorder.entity.*;
import com.example.jhscomputer.assemblyorder.enumpackage.AssemblyOrderStatus;
import com.example.jhscomputer.assemblyorder.repository.AssemblyOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminAssemblyOrderManagementService {

    @Autowired
    private AssemblyOrderRepository assemblyOrderRepository;

    // ---------------------------------------
    // [관리자] 전체 주문 목록을 반환
    // ---------------------------------------
    public List<AssemblyOrder> getAllOrders() {
        return assemblyOrderRepository.findAll();
    }

    // ---------------------------------------
    // [관리자] 주문 상태를 업데이트
    // ---------------------------------------
    public AssemblyOrder updateOrderStatus(Long orderId, AssemblyOrderStatus status, String requirements, String budget,
            String conceptColors) {
        AssemblyOrder order = assemblyOrderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. ID: " + orderId));
        // 상태 업데이트
        order.setStatus(status);
        // 요구사항 업데이트 (옵션)
        if (requirements != null && !requirements.trim().isEmpty()) {
            order.setRequirements(requirements.trim());
        }
        // 예산 업데이트 (옵션)
        if (budget != null && !budget.trim().isEmpty()) {
            order.setBudget(budget.trim());
        }
        // 구성 색상 업데이트 (옵션)
        if (conceptColors != null && !conceptColors.trim().isEmpty()) {
            order.setConceptColors(conceptColors.trim());
        }
        return assemblyOrderRepository.save(order);
    }

    // ---------------------------------------
    // [관리자] 특정 주문을 조회
    // ---------------------------------------
    public AssemblyOrder getOrderById(Long orderId) {
        return assemblyOrderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. ID: " + orderId));
    }

    // ---------------------------------------
    // [관리자] 특정 주문을 삭제
    // ---------------------------------------
    public void deleteOrder(Long orderId) {
        if (!assemblyOrderRepository.existsById(orderId)) {
            throw new IllegalArgumentException("삭제할 주문을 찾을 수 없습니다. ID: " + orderId);
        }
        assemblyOrderRepository.deleteById(orderId);
    }

    // ---------------------------------------
    // [관리자] 특정 유저가 등록한 주문 목록 조회
    // ---------------------------------------
    public List<AssemblyOrder> getOrdersByUser(Long userId) {
        return assemblyOrderRepository.findByUserId(userId);
    }
}