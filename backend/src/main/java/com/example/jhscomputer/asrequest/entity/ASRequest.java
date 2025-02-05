package com.example.jhscomputer.asrequest.entity;

import com.example.jhscomputer.asrequest.enumpackage.ASRequestStatus;
import com.example.jhscomputer.users.entity.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "as_requests")
@Getter
@Setter
@NoArgsConstructor
public class ASRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "contact_phone", nullable = false)
    private String contactPhone;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_type", nullable = false)
    private DeviceType deviceType;

    @Column(name = "issue_description", length = 1000, nullable = false)
    private String issueDescription;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "purchase_source", length = 255)
    private String purchaseSource;

    @Column(name = "quotation_attachment")
    private String quotationAttachment; // 파일 경로 또는 URL

    @Column(name = "manufacture_date")
    private LocalDate manufactureDate;

    @Column(name = "model_name", length = 255)
    private String modelName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ASRequestStatus status = ASRequestStatus.PENDING;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // DeviceType Enum 정의 (내부 또는 별도의 파일로 관리)
    public enum DeviceType {
        DESKTOP,
        LAPTOP
    }
}