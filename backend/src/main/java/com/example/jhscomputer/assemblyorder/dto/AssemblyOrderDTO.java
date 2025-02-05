package com.example.jhscomputer.assemblyorder.dto;

import com.example.jhscomputer.assemblyorder.entity.AssemblyOrder;
import com.example.jhscomputer.assemblyorder.entity.Purchase;
import com.example.jhscomputer.assemblyorder.entity.Quotation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssemblyOrderDTO {
    private AssemblyOrder order;
    private Quotation quotation;
    private Purchase purchase;

    public AssemblyOrderDTO(AssemblyOrder order, Quotation quotation, Purchase purchase) {
        this.order = order;
        this.quotation = quotation;
        this.purchase = purchase;
    }
}