package com.example.jhscomputer.assemblyorder.service;

import com.example.jhscomputer.assemblyorder.entity.Quotation;
import com.example.jhscomputer.assemblyorder.repository.QuotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuotationService {

    @Autowired
    private QuotationRepository quotationRepository;

    public Quotation findById(Long quotationId) {
        return quotationRepository.findById(quotationId).orElse(null);
    }

    public Quotation findByOrderId(Long orderId) {
        return quotationRepository.findByOrderOrderId(orderId);
    }

    public Quotation save(Quotation quotation) {
        return quotationRepository.save(quotation);
    }
}