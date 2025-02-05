package com.example.jhscomputer.assemblyorder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.jhscomputer.assemblyorder.enumpackage.AssemblyOrderStatus;
import com.example.jhscomputer.users.entity.User;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 사용자가 조립 주문을 넣을 때 필요한 정보를 담는 엔티티 클래스입니다.
 */
@Entity
@Table(name = "assembly_order")
@Getter
@Setter
@NoArgsConstructor
public class AssemblyOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    /**
     * 해당 주문을 등록한 사용자 정보 (N:1 관계)
     * user_id를 FK로 사용합니다.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String budget;

    private String favoriteGames;
    private String workType;
    private String cpuType;
    private String conceptColors;

    @Column(name = "purchase_date")
    @JsonIgnore // 직렬화에서 제외
    private LocalDate purchaseDate;
    private String coolerType;
    private String requirements;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssemblyOrderStatus status = AssemblyOrderStatus.PENDING; // 기본 주문 상태
}