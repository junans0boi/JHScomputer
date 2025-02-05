
package com.example.jhscomputer.admin.service;

import com.example.jhscomputer.asrequest.entity.ASRequest;
import com.example.jhscomputer.asrequest.enumpackage.ASRequestStatus;
import com.example.jhscomputer.asrequest.repository.ASRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AdminASRequestService {

    @Autowired
    private ASRequestRepository asRequestRepository;

    // ---------------------------------------
    // [관리자] 모든 AS 요청을 조회
    // ---------------------------------------
    public List<ASRequest> getAllRequests() {
        return asRequestRepository.findAll();
    }

    // ---------------------------------------
    // [관리자] 특정 사용자 ID의 AS 요청을 조회
    // ---------------------------------------
    public List<ASRequest> getRequestsByUserId(Long userId) {
        return asRequestRepository.findByUserUserId(userId);
    }

    // ---------------------------------------
    // [관리자] AS 요청을 생성하거나 업데이트 반영(저장)
    // ---------------------------------------
    public ASRequest saveRequest(ASRequest asRequest) {
        return asRequestRepository.save(asRequest);
    }

    // ---------------------------------------
    // [관리자] 특정 AS 요청을 조회합니다.
    // ---------------------------------------
    public Optional<ASRequest> getRequestById(Long requestId) {
        return asRequestRepository.findById(requestId);
    }

    // ---------------------------------------
    // [관리자] AS 요청의 상태를 업데이트합니다.
    // ---------------------------------------
    public ASRequest updateRequestStatus(Long requestId, ASRequestStatus status) {
        Optional<ASRequest> optionalRequest = asRequestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            ASRequest asRequest = optionalRequest.get();
            asRequest.setStatus(status);
            return asRequestRepository.save(asRequest);
        } else {
            throw new IllegalArgumentException("AS 요청을 찾을 수 없습니다. ID: " + requestId);
        }
    }

    // ---------------------------------------
    // [관리자] 특정 상태의 AS 요청을 조회합니다.
    // ---------------------------------------
    public List<ASRequest> getRequestsByStatus(ASRequestStatus status) {
        return asRequestRepository.findByStatus(status);
    }

    // ---------------------------------------
    // [관리자]AS 요청 업데이트 메서드 (추가 필드 처리)
    // ---------------------------------------
    public ASRequest updateASRequest(Long requestId, ASRequestStatus status, String issueDescription,
            LocalDate manufactureDate, String modelName, LocalDate purchaseDate, String purchaseSource) {
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

    // ---------------------------------------
    // [관리자] AS 요청을 삭제합니다.
    // @param requestId 삭제할 AS 요청 ID
    // @throws IllegalArgumentException 요청 ID가 유효하지 않을 경우
    // ---------------------------------------
    public void deleteRequest(Long requestId) {
        if (!asRequestRepository.existsById(requestId)) {
            throw new IllegalArgumentException("삭제할 AS 요청을 찾을 수 없습니다. ID: " + requestId);
        }
        asRequestRepository.deleteById(requestId);
    }
}