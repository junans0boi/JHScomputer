package com.example.jhscomputer.admin.controller;

import com.example.jhscomputer.assemblyorder.entity.AssemblyOrder;
import com.example.jhscomputer.assemblyorder.entity.Quotation;
import com.example.jhscomputer.assemblyorder.enumpackage.AssemblyOrderStatus;
import com.example.jhscomputer.assemblyorder.repository.QuotationRepository;
import com.example.jhscomputer.admin.service.AdminAssemblyOrderManagementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/orders")
public class OrderManagementController {

    @Autowired
    private AdminAssemblyOrderManagementService adminAssemblyOrderManagementService;

    @Autowired
    private QuotationRepository quotationRepository;

    // [Get] 조립 주문 요청 수정 페이지 이동
    @GetMapping("/{orderId}/edit")
    public String editOrderStatus(@PathVariable Long orderId,
            Model model, RedirectAttributes redirectAttributes) {
        AssemblyOrder order;
        try {
            order = adminAssemblyOrderManagementService.getOrderById(orderId);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/orders";
        }
        model.addAttribute("order", order);
        model.addAttribute("statuses", AssemblyOrderStatus.values());
        return "admin/assemblyorder/assemblyorder-form";
     
    }

    // [Post] 조립 주문 요청 수정 처리
    @PostMapping("/{orderId}/update")
    public String updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam("status") AssemblyOrderStatus status,
            @RequestParam(value = "requirements", required = false) String requirements,
            @RequestParam(value = "budget", required = false) String budget,
            @RequestParam(value = "conceptColors", required = false) String conceptColors,
            RedirectAttributes redirectAttributes) {
        try {
            adminAssemblyOrderManagementService.updateOrderStatus(orderId, status, requirements, budget, conceptColors);
            redirectAttributes.addFlashAttribute("success", "주문 상태가 업데이트되었습니다.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/orders";
    }

    // [Post] 조립 주문 요청 삭제 처리
    @PostMapping("/{orderId}/delete")
    public String deleteOrder(
            @PathVariable Long orderId,
            RedirectAttributes redirectAttributes) {
        try {
            // 1) 주문 조회
            AssemblyOrder order = adminAssemblyOrderManagementService.getOrderById(orderId);

            // 2) 연결된 견적 조회 후 삭제
            Quotation existingQuote = quotationRepository.findByOrderOrderId(orderId);
            if (existingQuote != null) {
                quotationRepository.delete(existingQuote);
            }

            // 3) 주문 삭제
            adminAssemblyOrderManagementService.deleteOrder(orderId);

            redirectAttributes.addFlashAttribute("success", "주문이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/orders";
    }
}
