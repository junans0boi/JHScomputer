package com.example.jhscomputer.assemblyorder.repository;

import com.example.jhscomputer.assemblyorder.entity.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotationRepository extends JpaRepository<Quotation, Long> {
    // 주문 ID로 견적 찾기 (필요시)
    Quotation findByOrderOrderId(Long orderId);
}