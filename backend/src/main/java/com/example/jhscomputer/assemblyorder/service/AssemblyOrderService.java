package com.example.jhscomputer.assemblyorder.service;

import com.example.jhscomputer.assemblyorder.entity.AssemblyOrder;
import com.example.jhscomputer.assemblyorder.enumpackage.AssemblyOrderStatus;
import com.example.jhscomputer.assemblyorder.repository.AssemblyOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssemblyOrderService {

    @Autowired
    private AssemblyOrderRepository assemblyOrderRepository;

    /**
     * 전체 주문 목록을 반환합니다.
     */
    public List<AssemblyOrder> getAllOrders() {
        return assemblyOrderRepository.findAll();
    }

    /**
     * 주문 상태를 업데이트합니다.
     *
     * @param orderId       업데이트할 주문 ID
     * @param status        새로운 주문 상태
     * @param requirements  추가 요구 사항
     * @param budget        예산
     * @param conceptColors 구성 색상
     * @return 업데이트된 주문 객체
     * @throws IllegalArgumentException 주문 ID가 유효하지 않을 경우
     */
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

    /**
     * 특정 주문을 조회합니다.
     *
     * @param orderId 조회할 주문 ID
     * @return 주문 객체
     * @throws IllegalArgumentException 주문 ID가 유효하지 않을 경우
     */
    public AssemblyOrder getOrderById(Long orderId) {
        return assemblyOrderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. ID: " + orderId));
    }

    /**
     * 특정 주문을 삭제합니다.
     *
     * @param orderId 삭제할 주문 ID
     * @throws IllegalArgumentException 주문 ID가 유효하지 않을 경우
     */
    public void deleteOrder(Long orderId) {
        if (!assemblyOrderRepository.existsById(orderId)) {
            throw new IllegalArgumentException("삭제할 주문을 찾을 수 없습니다. ID: " + orderId);
        }
        assemblyOrderRepository.deleteById(orderId);
    }

    // [추가] 특정 유저가 등록한 주문 목록 조회
    public List<AssemblyOrder> getOrdersByUser(Long userId) {
        return assemblyOrderRepository.findByUserId(userId);
    }
}