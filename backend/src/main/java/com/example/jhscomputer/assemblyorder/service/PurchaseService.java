package com.example.jhscomputer.assemblyorder.service;

import com.example.jhscomputer.assemblyorder.entity.Purchase;
import com.example.jhscomputer.assemblyorder.entity.Quotation;
import com.example.jhscomputer.users.entity.User;
import com.example.jhscomputer.assemblyorder.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    /**
     * 결제(Purchase) 생성
     */
    public Purchase createPurchase(User user, Quotation quotation, int totalPaid) {
        // 이미 해당 Quotation으로 구매가 존재한다면 예외 처리
        Purchase existing = purchaseRepository.findByQuotationQuotationId(quotation.getQuotationId());
        if (existing != null) {
            throw new IllegalStateException("이미 구매가 진행된 견적입니다.");
        }

        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setQuotation(quotation);
        purchase.setTotalPaid(totalPaid);
        purchase.setPurchasedAt(LocalDateTime.now());
        return purchaseRepository.save(purchase);
    }

    /**
     * 특정 유저의 구매 목록 조회
     */
    public List<Purchase> getPurchasesByUser(Long userId) {
        return purchaseRepository.findByUserUserId(userId);
    }

    /**
     * 구매 상세
     */
    public Purchase getPurchase(Long purchaseId) {
        return purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new IllegalArgumentException("구매 정보를 찾을 수 없습니다. ID=" + purchaseId));
    }

    /**
     * 특정 견적에 대한 구매 조회
     */
    public Purchase getPurchaseByQuotationId(Long quotationId) {
        return purchaseRepository.findByQuotationQuotationId(quotationId);
    }
}