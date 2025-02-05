package com.example.jhscomputer.assemblyorder.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuotationDTO {
    private Long quotationId;  // Add this field
    private Long orderId;
    private int totalPrice;
    private List<Item> items;

    @Data
    public static class Item {
        private String category;
        private String productName;
        private int quantity;
        private int price;
    }
}