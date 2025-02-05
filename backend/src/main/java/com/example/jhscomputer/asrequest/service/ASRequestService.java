package com.example.jhscomputer.asrequest.service;

import com.example.jhscomputer.asrequest.entity.ASRequest;
import com.example.jhscomputer.asrequest.enumpackage.ASRequestStatus;
import com.example.jhscomputer.asrequest.repository.ASRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ASRequestService {

    private final ASRequestRepository asRequestRepository;

    @Autowired
    public ASRequestService(ASRequestRepository asRequestRepository) {
        this.asRequestRepository = asRequestRepository;
    }

    public List<ASRequest> getAllRequests() {
        return asRequestRepository.findAll();
    }

    public List<ASRequest> getRequestsByUserId(Long userId) {
        return asRequestRepository.findByUserUserId(userId);
    }

    @Transactional
    public ASRequest saveRequest(ASRequest asRequest) {
        return asRequestRepository.save(asRequest);
    }

    public Optional<ASRequest> getRequestById(Long requestId) {
        return asRequestRepository.findById(requestId);
    }

    @Transactional
    public ASRequest updateRequestStatus(Long requestId, ASRequestStatus status) {
        ASRequest asRequest = asRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("AS 요청을 찾을 수 없습니다. ID: " + requestId));
        asRequest.setStatus(status);
        return asRequestRepository.save(asRequest);
    }

    @Transactional
    public ASRequest updateASRequest(Long requestId, ASRequestStatus status, String issueDescription,
                                     LocalDate manufactureDate, String modelName,
                                     LocalDate purchaseDate, String purchaseSource) {
        ASRequest asRequest = asRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("AS 요청을 찾을 수 없습니다. ID: " + requestId));

        asRequest.setStatus(status);
        asRequest.setIssueDescription(issueDescription);

        if (asRequest.getDeviceType() == ASRequest.DeviceType.LAPTOP) {
            if (manufactureDate != null) {
                asRequest.setManufactureDate(manufactureDate);
            }
            asRequest.setModelName(modelName);
        } else if (asRequest.getDeviceType() == ASRequest.DeviceType.DESKTOP) {
            if (purchaseDate != null) {
                asRequest.setPurchaseDate(purchaseDate);
            }
            asRequest.setPurchaseSource(purchaseSource);
        }
        return asRequestRepository.save(asRequest);
    }

    @Transactional
    public void deleteRequest(Long requestId) {
        if (!asRequestRepository.existsById(requestId)) {
            throw new IllegalArgumentException("삭제할 AS 요청을 찾을 수 없습니다. ID: " + requestId);
        }
        asRequestRepository.deleteById(requestId);
    }
}
