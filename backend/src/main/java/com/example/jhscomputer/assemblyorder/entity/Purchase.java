package com.example.jhscomputer.assemblyorder.entity;

import com.example.jhscomputer.users.entity.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchases")
@Getter @Setter
@NoArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseId;

    // 누가 구매했는지 (유저)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; 

    // 어떤 견적(Quotation)을 구매했는지
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quotation_id")
    private Quotation quotation;

    // 결제 완료 시각
    private LocalDateTime purchasedAt;

    // 결제 금액(최종 금액)
    private Integer totalPaid;
    
    // 결제 수단, 상태 등 필요시 추가
}