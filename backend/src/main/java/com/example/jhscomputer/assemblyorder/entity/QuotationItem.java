package com.example.jhscomputer.assemblyorder.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

// QuotationItem.java
@Entity
@Table(name = "quotation_item")
@Getter
@Setter
@NoArgsConstructor
public class QuotationItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quotation_id")
    @JsonBackReference // 직렬화 시 이 필드는 무시됨
    private Quotation quotation;

    private String category;
    private String productName;
    private int quantity;
    private int price;
}