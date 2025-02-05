package com.example.jhscomputer.assemblyorder.repository;

import com.example.jhscomputer.assemblyorder.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    // 특정 유저의 구매 내역
    List<Purchase> findByUserUserId(Long userId);

    // 특정 견적에 대한 구매 내역 (1:1이므로 0 또는 1개)
    Purchase findByQuotationQuotationId(Long quotationId);
}