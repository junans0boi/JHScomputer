package com.example.jhscomputer.admin.controller;

import com.example.jhscomputer.assemblyorder.entity.AssemblyOrder;
import com.example.jhscomputer.assemblyorder.entity.Quotation;
import com.example.jhscomputer.assemblyorder.entity.QuotationItem;
import com.example.jhscomputer.assemblyorder.dto.QuotationDTO;
import com.example.jhscomputer.admin.service.AdminAssemblyOrderManagementService;
import com.example.jhscomputer.assemblyorder.repository.QuotationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Controller
@RequestMapping("/admin/estimate")
public class OrderQuotationManagementController {

    // 관리용 주문 서비스 (주문 조회 등)
    @Autowired
    private AdminAssemblyOrderManagementService adminAssemblyOrderService;
    
    // 견적 관련 Repository
    @Autowired
    private QuotationRepository quotationRepository;
    
    // [Get] 견적표 작성 페이지 이동
    @GetMapping("/{orderId}")
    public String showQuotationForm(@PathVariable Long orderId, Model model) {
        // 주문 로드 (관리자용 서비스 사용)
        AssemblyOrder order = adminAssemblyOrderService.getOrderById(orderId);
        model.addAttribute("order", order);
    
        // 해당 주문에 대한 기존 견적이 있는지 확인
        Quotation existingQuote = quotationRepository.findByOrderOrderId(orderId);
        model.addAttribute("existingQuote", existingQuote);
    
        return "admin/assemblyorder/assemblyorder-pcpart-form";
    }
    
    // [Post] 견적서 저장/수정 [AJAX]
    @PostMapping("/orders/{orderId}/estimate/save")
    @ResponseBody
    public Map<String, Object> saveQuotation(
            @PathVariable Long orderId,
            @RequestBody QuotationDTO dto) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 주문 조회 (관리자 서비스 사용)
            AssemblyOrder order = adminAssemblyOrderService.getOrderById(orderId);
    
            // 기존 견적 여부 확인
            Quotation quotation = quotationRepository.findByOrderOrderId(orderId);
            if (quotation == null) {
                quotation = new Quotation();
                quotation.setOrder(order);
            } else {
                // 기존 아이템들 삭제
                quotation.getItems().clear();
                quotationRepository.save(quotation);
            }
    
            quotation.setTotalPrice(dto.getTotalPrice());
    
            List<QuotationItem> items = new ArrayList<>();
            for (QuotationDTO.Item itemDto : dto.getItems()) {
                QuotationItem item = new QuotationItem();
                item.setQuotation(quotation);
                item.setCategory(itemDto.getCategory());
                item.setProductName(itemDto.getProductName());
                item.setPrice(itemDto.getPrice());
                item.setQuantity(itemDto.getQuantity());
                items.add(item);
            }
            quotation.setItems(items);
    
            quotationRepository.save(quotation);
    
            result.put("success", true);
            result.put("quotationId", quotation.getQuotationId());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
